package uz.smartmuslim.fanlarakademiyasi.data.remote.response

import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.FileEntity

data class FileResponse(
    val hashId: String,
    val name: String,
    val extension: String,
    val fileSize: Long,
    val data: String,
    val fileUrl: String,
    val uploadPath: String
) {
    fun toEntity() = FileEntity(
        hashId,
        name,
        extension,
        fileSize,
        data,
        convertShortUrlToRealUrl(fileUrl),
        uploadPath,
        0,
        0
    )

    fun convertShortUrlToRealUrl(shortUrl: String): String {
        val asteriskCount = shortUrl.count { it == '*' }
        val realUrl = shortUrl.replace("*", "")
        return realUrl.substring(0, realUrl.length - asteriskCount)
    }
}