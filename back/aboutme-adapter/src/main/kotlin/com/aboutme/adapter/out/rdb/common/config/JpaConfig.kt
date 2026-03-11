package com.aboutme.adapter.out.rdb.common.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.aboutme.adapter.out.rdb"])
@EntityScan(basePackages = ["com.aboutme.adapter.out.rdb"])
@Configuration
class JpaConfig
