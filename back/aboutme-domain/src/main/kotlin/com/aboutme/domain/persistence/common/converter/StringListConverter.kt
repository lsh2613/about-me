package com.aboutme.domain.persistence.common.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * List<String>을 구분자(,)로 연결하여 DB에 저장하고,
 * DB에서 읽어올 때는 구분자로 분리하여 List<String>으로 변환하는 Converter
 *
 * 사용 예시:
 * ```
 * @Convert(converter = StringListConverter::class)
 * @Column(columnDefinition = "VARCHAR")
 * val emails: List<String>
 * ```
 */
@Converter
class StringListConverter : AttributeConverter<List<String>, String> {
    companion object {
        private const val DELIMITER = ","
    }

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        return attribute?.joinToString(DELIMITER)
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return dbData?.split(DELIMITER)
            ?.filter { it.isNotBlank() }
            ?: emptyList()
    }
}
