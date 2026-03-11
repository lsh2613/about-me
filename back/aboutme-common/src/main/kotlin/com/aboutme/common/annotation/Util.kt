package com.aboutme.common.annotation

/**
 * 유틸리티 클래스를 명시하는 어노테이션입니다.
 *
 *
 * 이 어노테이션은 컴포넌트 등록이 필요 없는, **static 메소드만 제공하는 유틸리티 클래스**에 사용합니다.<br></br>
 * Spring 빈으로 등록이 필요한 경우에는 [Helper] 어노테이션을 사용하세요.
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Util
