package com.aboutme.app.common.util

import com.aboutme.common.annotation.Util
import com.aboutme.core.essay.domain.Essay
import com.appmattus.kotlinfixture.Fixture

@Util
class EssayFixture {
    companion object {
        fun create(
            id: Long? = 1L,
            title: String = "자기소개서",
            content: String = "내용",
            seq: Int = 1,
        ): Essay {
            val fixture = Fixture()
            return fixture<Essay> {
                property(Essay::id) { id }
                property(Essay::title) { title }
                property(Essay::content) { content }
                property(Essay::seq) { seq }
            }
        }
    }
}
