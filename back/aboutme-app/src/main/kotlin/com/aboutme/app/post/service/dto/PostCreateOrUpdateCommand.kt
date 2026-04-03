package com.aboutme.app.post.service.dto

import java.net.URL

data class PostCreateOrUpdateCommand(
    val title: String,
    val content: String,
    val referenceUrls: List<URL>? = null,
)
