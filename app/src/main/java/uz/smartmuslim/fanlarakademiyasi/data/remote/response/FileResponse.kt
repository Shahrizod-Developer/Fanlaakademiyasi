package uz.smartmuslim.fanlarakademiyasi.data.remote.response

import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.FileEntity

data class FileResponse(
    val id: String,
    val uploadPath: String,
    val hashId: String,
    val name: String,
    val fileSize: Long,
    val contentType: String,
    val extension: String,
    val fileUrl: String
) {
    fun toEntity() = FileEntity(
        id,
        uploadPath,
        hashId,
        name,
        fileSize,
        contentType,
        download = 0,
        extension,
        fileUrl,
        isDownload = 0
    )
}