package com.aboutme.app.common.util

import com.aboutme.common.annotation.Util
import com.aboutme.core.instruction.domain.Instruction
import com.aboutme.core.instruction.vo.Email
import com.appmattus.kotlinfixture.Fixture

@Util
class InstructionFixture {
    companion object {
        fun createMock1(): Instruction {
            val fixture = Fixture()
            return fixture<Instruction> {
                property(Instruction::name) { "홍길동" }
                property(Instruction::emails) {
                    listOf(
                        Email("mock1@test.com"),
                        Email("mock1@example.com"),
                    )
                }
                property(Instruction::region) { "강남" }
                property(Instruction::education) { "컴퓨터공학과" }
                property(Instruction::skills) { listOf("Kotlin", "Spring Boot") }
                property(Instruction::profileImagePath) { "https://example.com/profile1.jpg" }
            }
        }

        fun createMock2(): Instruction {
            val fixture = Fixture()
            return fixture<Instruction> {
                property(Instruction::name) { "전우치" }
                property(Instruction::emails) {
                    listOf(
                        Email("mock2@test.com"),
                        Email("mock2@example.com"),
                        Email("mock2@google.com"),
                    )
                }
                property(Instruction::region) { "수원" }
                property(Instruction::education) { "전자공학과" }
                property(Instruction::skills) { listOf("Type Script", "React") }
                property(Instruction::profileImagePath) { "https://example.com/profile2.jpg" }
            }
        }
    }
}
