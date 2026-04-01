package com.aboutme.core.file.domain

enum class FileUploadType(
    val mime: MIME,
    val subPath: String,
) {
    PROFILE(MIME.IMAGE, "profile"),
    POST(MIME.IMAGE, "post"),
    ;

    fun isValidMimeType(contentType: String): Boolean {
        return contentType.startsWith(this.mime.type)
    }

    enum class MIME(val type: String) {
        IMAGE("image"),
    }
}
