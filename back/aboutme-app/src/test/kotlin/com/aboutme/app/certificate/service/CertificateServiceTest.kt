package com.aboutme.app.certificate.service

import com.aboutme.app.certificate.port.out.CertificateCommandPort
import com.aboutme.app.certificate.port.out.CertificateQueryPort
import com.aboutme.app.certificate.service.dto.command.CertificateSyncCommand
import com.aboutme.app.common.util.CertificateFixture
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class CertificateServiceTest : DescribeSpec({
    val certificateQueryPort = mockk<CertificateQueryPort>(relaxed = true)
    val certificateCommandPort = mockk<CertificateCommandPort>(relaxed = true)
    val certificateService = CertificateService(certificateQueryPort, certificateCommandPort)

    fun createCommand(
        id: Long? = null,
        seq: Int,
    ) = CertificateSyncCommand(
        id = id,
        name = "자격증 $seq",
        issuer = "발급기관 $seq",
        issueDate = LocalDate.parse("2023-01-0$seq"),
        expireDate = LocalDate.parse("2024-01-0$seq"),
        seq = seq,
    )

    describe("자격증 동기화(생성/수정/삭제) 검증") {
        context("저장되어 있는 자격증의 id가 command에 포함되어 있지 않다면") {
            listOf(
                CertificateFixture.create(id = 1L),
                CertificateFixture.create(id = 2L),
                CertificateFixture.create(id = 3L),
            ).also { every { certificateQueryPort.findAll() } returns it }

            listOf(
                createCommand(id = 1L, seq = 1),
                createCommand(id = 2L, seq = 2),
            ).also { certificateService.sync(it) }

            it("command에 포함되지 않은 기존 자격증은 삭제된다") {
                verify(exactly = 1) { certificateCommandPort.delete(listOf(3L)) }
            }
        }

        context("command에 id가 null이라면") {
            every { certificateQueryPort.findAll() } returns emptyList()

            listOf(
                createCommand(seq = 1),
                createCommand(seq = 2),
            ).also { certificateService.sync(it) }

            it("새로운 자격증이 생성된다") {
                verify(exactly = 2) { certificateCommandPort.save(any()) }
            }
        }

        context("command에 id가 존재한다면") {
            val certificate1 =
                CertificateFixture.create(id = 1L).also {
                    every { certificateQueryPort.findOrThrow(it.id!!) } returns it
                }
            val certificate2 =
                CertificateFixture.create(id = 2L).also {
                    every { certificateQueryPort.findOrThrow(it.id!!) } returns it
                }
            every { certificateQueryPort.findAll() } returns listOf(certificate1, certificate2)

            listOf(
                createCommand(id = 1L, seq = 1),
                createCommand(id = 2L, seq = 2),
            ).also { certificateService.sync(it) }

            it("기존 자격증이 수정된다") {
                verify { certificateCommandPort.update(certificate1) }
                verify { certificateCommandPort.update(certificate2) }
            }
        }
    }

    describe("자격증 전체 삭제") {
        certificateService.deleteAll()

        it("모든 자격증이 삭제된다") {
            verify(exactly = 1) { certificateCommandPort.deleteAll() }
        }
    }
})
