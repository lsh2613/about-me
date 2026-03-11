package com.aboutme.common.annotation

import org.springframework.stereotype.Component

/**
 * 컴포넌트 등록이 필요한 유틸리티성 클래스를 명시하는 어노테이션입니다.
 *
 * 이 어노테이션은 Spring 빈으로 등록되어야 하며, 상태를 가지지 않는 **Helper** 역할의 클래스를 나타냅니다.<br></br>
 * 컴포넌트 등록이 필요 없는 static 메소드만 제공하는 경우에는 [Util] 어노테이션을 사용하세요.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class Helper
