package com.aboutme.app.essay.service

import com.aboutme.app.common.util.EssayFixture
import com.aboutme.app.essay.port.out.EssayCommandPort
import com.aboutme.app.essay.port.out.EssayQueryPort
import com.aboutme.app.essay.service.dto.command.EssaySyncCommand
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class EssayServiceTest : DescribeSpec({
    val essayQueryPort = mockk<EssayQueryPort>(relaxed = true)
    val essayCommandPort = mockk<EssayCommandPort>(relaxed = true)
    val essayService = EssayService(essayQueryPort, essayCommandPort)

    fun createCommand(
        id: Long? = null,
        seq: Int,
    ) = EssaySyncCommand(
        id = id,
        title = "에세이 $seq",
        content = "내용 $seq",
        seq = seq,
    )

    describe("에세이 동기화(생성/수정/삭제) 검증") {
        context("저장되어 있는 에세이의 id가 command에 포함되어 있지 않다면") {
            listOf(
                EssayFixture.create(id = 1L),
                EssayFixture.create(id = 2L),
                EssayFixture.create(id = 3L),
            ).also { every { essayQueryPort.findAll() } returns it }

            listOf(
                createCommand(id = 1L, seq = 1),
                createCommand(id = 2L, seq = 2),
            ).also { essayService.sync(it) }

            it("command에 포함되지 않은 기존 에세이는 삭제된다") {
                verify(exactly = 1) { essayCommandPort.delete(listOf(3L)) }
            }
        }

        context("command에 id가 null이라면") {
            every { essayQueryPort.findAll() } returns emptyList()

            listOf(
                createCommand(seq = 1),
                createCommand(seq = 2),
            ).also { essayService.sync(it) }

            it("새로운 에세이가 생성된다") {
                verify(exactly = 2) { essayCommandPort.save(any()) }
            }
        }

        context("command에 id가 존재한다면") {
            val essay1 =
                EssayFixture.create(id = 1L).also {
                    every { essayQueryPort.findOrThrow(it.id!!) } returns it
                }
            val essay2 =
                EssayFixture.create(id = 2L).also {
                    every { essayQueryPort.findOrThrow(it.id!!) } returns it
                }
            every { essayQueryPort.findAll() } returns listOf(essay1, essay2)

            listOf(
                createCommand(id = 1L, seq = 1),
                createCommand(id = 2L, seq = 2),
            ).also { essayService.sync(it) }

            it("기존 에세이가 수정된다") {
                verify { essayCommandPort.update(essay1) }
                verify { essayCommandPort.update(essay2) }
            }
        }
    }

    describe("에세이 조회") {
        val essay1 = EssayFixture.create(id = 1L, seq = 1, title = "에세이1")
        val essay2 = EssayFixture.create(id = 2L, seq = 2, title = "에세이2")

        every { essayQueryPort.findAll() } returns listOf(essay1, essay2)
        val details = essayService.readAll()

        it("모든 에세이를 상세 응답으로 반환한다") {
            details.forExactly(1) {
                it.id shouldBe essay1.id
                it.title shouldBe essay1.title
                it.seq shouldBe essay1.seq
            }
            details.forExactly(1) {
                it.id shouldBe essay2.id
                it.title shouldBe essay2.title
                it.seq shouldBe essay2.seq
            }
        }
    }
})
