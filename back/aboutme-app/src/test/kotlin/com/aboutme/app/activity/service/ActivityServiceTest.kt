package com.aboutme.app.activity.service

import com.aboutme.app.activity.port.out.ActivityCommandPort
import com.aboutme.app.activity.port.out.ActivityQueryPort
import com.aboutme.app.activity.service.dto.command.ActivitySyncCommand
import com.aboutme.app.common.util.ActivityFixture
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.activity.domain.ActivityType
import com.aboutme.core.activity.error.ActivityErrorCode
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class ActivityServiceTest : DescribeSpec({
    val activityQueryPort = mockk<ActivityQueryPort>(relaxed = true)
    val activityCommandPort = mockk<ActivityCommandPort>(relaxed = true)
    val activityService = ActivityService(activityQueryPort, activityCommandPort)

    fun createCommand(
        id: Long? = null,
        seq: Int,
    ) = ActivitySyncCommand(
        activityId = id,
        name = "활동 $seq",
        activityType = ActivityType.PART_TIME,
        startDate = LocalDate.now(),
        endDate = LocalDate.now(),
        description = "설명",
        seq = seq,
    )

    describe("활동이력 동기화 검증") {
        describe("seq 검증") {
            context("seq가 1부터 시작하고 누락이 없으면") {
                val commands =
                    listOf(
                        createCommand(seq = 2),
                        createCommand(seq = 1),
                        createCommand(seq = 3),
                    )
                it("예외가 발생하지 않는다") {
                    shouldNotThrowAny { activityService.sync(commands) }
                }
            }

            context("seq가 1부터 시작하지 않으면") {
                it("예외가 발생한다") {
                    val e =
                        shouldThrow<GlobalException> {
                            activityService.sync(listOf(createCommand(seq = 2)))
                        }
                    e.errorCode shouldBe ActivityErrorCode.INVALID_SEQ
                }
            }

            context("seq 사이에 누락이 있거나 중복이면") {
                val missingSeqCommands = listOf(createCommand(seq = 1), createCommand(seq = 3))
                val duplicatedSeqCommands =
                    listOf(createCommand(seq = 1), createCommand(seq = 2), createCommand(seq = 2))

                it("예외가 발생한다") {
                    shouldThrow<GlobalException> { activityService.sync(missingSeqCommands) }
                    shouldThrow<GlobalException> { activityService.sync(duplicatedSeqCommands) }
                }
            }

            context("활동 이력이 하나도 존재하지 않으면") {
                it("예외가 발생한다") {
                    shouldThrow<IllegalArgumentException> { activityService.sync(emptyList()) }
                }
            }
        }

        describe("LocalDate 검증") {
            context("종료일이 시작일보다 이전이면") {
                val now = LocalDate.now()
                val commands =
                    listOf(
                        ActivitySyncCommand(
                            activityId = null,
                            name = "활동",
                            activityType = ActivityType.PART_TIME,
                            startDate = now,
                            endDate = now.minusDays(1),
                            description = "설명",
                            seq = 1,
                        ),
                    )

                it("예외가 발생한다") {
                    val e = shouldThrow<GlobalException> { activityService.sync(commands) }
                    e.errorCode shouldBe ActivityErrorCode.INVALID_PERIOD
                }
            }

            context("종료일이 존재하지 않거나 시작일 이후이면") {
                it("예외가 발생하지 않는다") {
                    val now = LocalDate.now()
                    val openEnded = createCommand(seq = 1).copy(endDate = null)
                    val afterStart = createCommand(seq = 2).copy(endDate = now.plusDays(1))

                    shouldNotThrowAny { activityService.sync(listOf(openEnded, afterStart)) }
                }
            }
        }

        describe("기존 활동 이력과 비교하여 삭제/수정/생성 수행") {
            context("저장되어 있는 활동 이력의 id가 command에 포함되어 있지 않다면") {
                val existingActivities =
                    listOf(
                        ActivityFixture.create(id = 1L),
                        ActivityFixture.create(id = 2L),
                        ActivityFixture.create(id = 3L),
                    )
                every { activityQueryPort.findAll() } returns existingActivities

                val commands =
                    listOf(
                        createCommand(id = 1L, seq = 1),
                        createCommand(id = 2L, seq = 2),
                    )

                it("command에 포함되어 있지 않은 기존 활동 이력은 삭제된다") {
                    activityService.sync(commands)
                    verify(exactly = 1) { activityCommandPort.delete(listOf(3L)) }
                }
            }

            context("command에 id가 null이라면") {
                val commands =
                    listOf(
                        createCommand(seq = 1),
                        createCommand(seq = 2),
                    )

                it("새로운 활동 이력이 생성된다") {
                    activityService.sync(commands)
                    verify(exactly = 2) { activityCommandPort.save(any()) }
                }
            }

            context("command에 id가 존재한다면") {
                val activity1 = ActivityFixture.create(id = 1L)
                val activity2 = ActivityFixture.create(id = 2L)
                every { activityQueryPort.findOrThrow(activity1.id!!) } returns activity1
                every { activityQueryPort.findOrThrow(activity2.id!!) } returns activity2

                val commands =
                    listOf(
                        createCommand(id = 1L, seq = 1),
                        createCommand(id = 2L, seq = 2),
                    )

                it("기존 활동 이력이 수정된다") {
                    activityService.sync(commands)
                    verify { activityCommandPort.update(activity1) }
                    verify { activityCommandPort.update(activity2) }
                }
            }
        }
    }

    describe("활동 이력 조회") {
        val activity1 = ActivityFixture.create(id = 1L, seq = 1, name = "활동1")
        val activity2 = ActivityFixture.create(id = 2L, seq = 2, name = "활동2")

        every { activityQueryPort.findAll() } returns listOf(activity1, activity2)
        val details = activityService.readDetails()

        it("모든 활동 이력을 상세 응답으로 반환한다") {
            details.forExactly(1) {
                it.id shouldBe activity1.id
                it.name shouldBe activity1.name
                it.seq shouldBe activity1.seq
            }
            details.forExactly(1) {
                it.id shouldBe activity2.id
                it.name shouldBe activity2.name
                it.seq shouldBe activity2.seq
            }
        }
    }
})
