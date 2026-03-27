package com.aboutme.app.common.util

import com.aboutme.common.annotation.Util
import com.aboutme.core.profile.domain.Profile
import com.aboutme.core.profile.vo.Email
import com.appmattus.kotlinfixture.Fixture

@Util
class ProfileFixture {
    companion object {
        fun createMock1(): Profile {
            val fixture = Fixture()
            return fixture<Profile> {
                property(Profile::name) { "홍길동" }
                property(Profile::emails) {
                    listOf(
                        Email("mock1@test.com"),
                        Email("mock1@example.com"),
                    )
                }
                property(Profile::region) { "강남" }
                property(Profile::education) { "컴퓨터공학과" }
                property(Profile::skills) { listOf("Kotlin", "Spring Boot") }
                property(Profile::profileImagePath) { "https://example.com/profile1.jpg" }
            }
        }

        fun createMock2(): Profile {
            val fixture = Fixture()
            return fixture<Profile> {
                property(Profile::name) { "전우치" }
                property(Profile::emails) {
                    listOf(
                        Email("mock2@test.com"),
                        Email("mock2@example.com"),
                        Email("mock2@google.com"),
                    )
                }
                property(Profile::region) { "수원" }
                property(Profile::education) { "전자공학과" }
                property(Profile::skills) { listOf("Type Script", "React") }
                property(Profile::profileImagePath) { "https://example.com/profile2.jpg" }
            }
        }
    }
}
