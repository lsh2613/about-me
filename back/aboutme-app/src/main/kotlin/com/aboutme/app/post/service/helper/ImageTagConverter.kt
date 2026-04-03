package com.aboutme.app.post.service.helper

import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.file.service.util.FileManager
import com.aboutme.common.annotation.Helper
import com.aboutme.core.file.domain.FileUploadType
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URI
import java.nio.file.Path
import java.util.Base64
import java.util.UUID

@Helper
class ImageTagConverter(
    private val fileNamer: FileNamer,
) {
    companion object {
        private const val ENCODED_IMAGE_TAG_FORMAT = "img[src^=data:image]"
        private val BASE64_DATA_PATTERN = Regex("^data:(.+);base64,(.+)$")
    }

    fun base64ToUri(
        doc: Document,
        postId: Long,
    ) {
        val uploadDirPath = fileNamer.createUploadDirPath(FileUploadType.POST, postId)

        encodedImageElements(doc).forEach { imgElement ->
            val fileName = fileName(imgElement)
            val dataPart = dataPart(imgElement)

            if (dataPart.isNullOrEmpty() || fileName.isNullOrBlank()) {
                return@forEach
            }

            val fileUri = upload(dataPart, uploadDirPath.resolve(fileName))
            imgElement.attr("src", fileUri.toString())
        }
    }

    private fun encodedImageElements(doc: Document) = doc.select(ENCODED_IMAGE_TAG_FORMAT)

    private fun upload(
        dataPart: String,
        filePath: Path,
    ): URI {
        val bytes = Base64.getDecoder().decode(dataPart)
        FileManager.upload(bytes.inputStream(), filePath)
        return fileNamer.toUri(filePath)
    }

    private fun fileName(img: Element): String? {
        val match = findBase64Data(img) ?: return null
        val mime = match.groupValues[1]
        val ext = mime.substringAfter("/").lowercase()
        return "${UUID.randomUUID()}.$ext"
    }

    private fun dataPart(img: Element): String? {
        val match = findBase64Data(img) ?: return null
        return match.groupValues[2]
    }

    private fun findBase64Data(img: Element): MatchResult? {
        val src = img.attr("src")
        return BASE64_DATA_PATTERN.find(src)
    }
}
