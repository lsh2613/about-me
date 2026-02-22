package com.aboutme.domain.persistence.resume.repository

import com.aboutme.domain.persistence.resume.entity.Certificate
import org.springframework.data.jpa.repository.JpaRepository

interface CertificateRepository : JpaRepository<Certificate, Long>
