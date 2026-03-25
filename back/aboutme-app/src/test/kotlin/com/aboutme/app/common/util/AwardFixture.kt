package com.aboutme.app.common.util

import com.aboutme.common.annotation.Util
import com.aboutme.core.award.domain.Award
import com.appmattus.kotlinfixture.Fixture
import java.time.LocalDate

@Util
class AwardFixture {
    companion object {
        fun create(
            id: Long? = 1L,
            name: String = "수상",
            issuer: String = "발급처",
            issueDate: LocalDate = LocalDate.now(),
            description: String = "설명",
            seq: Int = 1,
        ): Award {
            val fixture = Fixture()
            return fixture<Award> {
                property(Award::id) { id }
                property(Award::name) { name }
                property(Award::issuer) { issuer }
                property(Award::issueDate) { issueDate }
                property(Award::description) { description }
                property(Award::seq) { seq }
            }
        }
    }
}
