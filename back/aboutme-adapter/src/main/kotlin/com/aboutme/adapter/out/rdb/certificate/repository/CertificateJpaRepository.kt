package com.aboutme.adapter.out.rdb.certificate.repository

import com.aboutme.adapter.out.rdb.certificate.entity.CertificateEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CertificateJpaRepository : JpaRepository<CertificateEntity, Long>
