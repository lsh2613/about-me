package com.aboutme.app.award.service

import com.aboutme.app.award.port.out.AwardCommandPort
import com.aboutme.app.award.port.out.AwardQueryPort
import com.aboutme.app.award.service.dto.command.AwardSyncCommand
import com.aboutme.app.common.util.AwardFixture
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
class AwardServiceTest : DescribeSpec({
    val awardCommandPort = mockk<AwardCommandPort>(relaxed = true)
    val awardQueryPort = mockk<AwardQueryPort>(relaxed = true)
    val awardService = AwardService(awardCommandPort, awardQueryPort)

    fun createCommand(
        id: Long? = null,
        seq: Int,
    ) = AwardSyncCommand(
        id = id,
        name = "수상 $seq",
        issuer = "발급처",
        issueDate = LocalDate.now(),
        description = "설명",
        seq = seq,
    )

    describe("수상이력 동기화(생성/수정/삭제) 검증") {
        context("저장되어 있는 수상이력의 id가 command에 포함되어 있지 않다면") {
            listOf(AwardFixture.create(id = 1L), AwardFixture.create(id = 2L), AwardFixture.create(id = 3L))
                .also {
                    every { awardQueryPort.findAll() } returns it
                }

            val commands = listOf(createCommand(id = 1L, seq = 1), createCommand(id = 2L, seq = 2))
            it("command에 포함되지 않은 기존 수상이력은 삭제된다") {
                awardService.sync(commands)
                verify(exactly = 1) { awardCommandPort.delete(listOf(3L)) }
            }
        }

        context("command에 id가 null이라면") {
            val commands = listOf(createCommand(seq = 1), createCommand(seq = 2))

            it("새로운 수상이력이 생성된다") {
                awardService.sync(commands)
                verify(exactly = 2) { awardCommandPort.save(any()) }
            }
        }

        context("command에 id가 존재한다면") {
            val award1 =
                AwardFixture.create(id = 1L).also {
                    every { awardQueryPort.findOrThrow(it.id!!) } returns it
                }
            val award2 =
                AwardFixture.create(id = 2L).also {
                    every { awardQueryPort.findOrThrow(it.id!!) } returns it
                }

            val commands = listOf(createCommand(id = 1L, seq = 1), createCommand(id = 2L, seq = 2))

            awardService.sync(commands)
            it("기존 수상이력이 수정된다") {
                verify { awardCommandPort.update(award1) }
                verify { awardCommandPort.update(award2) }
            }
        }
    }

    describe("수상이력 조회") {
        val award1 = AwardFixture.create(id = 1L, seq = 1, name = "수상1")
        val award2 = AwardFixture.create(id = 2L, seq = 2, name = "수상2")

        every { awardQueryPort.findAll() } returns listOf(award1, award2)

        val details = awardService.readDetails()
        it("모든 수상이력을 상세 응답으로 반환한다") {
            details.forExactly(1) {
                it.id shouldBe award1.id
                it.name shouldBe award1.name
                it.seq shouldBe award1.seq
            }
            details.forExactly(1) {
                it.id shouldBe award2.id
                it.name shouldBe award2.name
                it.seq shouldBe award2.seq
            }
        }
    }
})
