package com.aboutme.app.coverletter.port.out

import com.aboutme.core.coverletter.domain.CoverLetter

interface CoverLetterQueryPort {
    fun loadAll(): List<CoverLetter>
}
