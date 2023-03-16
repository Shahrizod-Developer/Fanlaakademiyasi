package uz.smartmuslim.fanlarakademiyasi.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData


@Entity(tableName = "file")
data class FileEntity(

    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "upload_path")
    val uploadPath: String,
    @ColumnInfo(name = "hash_id")
    val hashId: String,
    val name: String,
    @ColumnInfo(name = "file_size")
    val fileSize: Long,
    @ColumnInfo(name = "content_type")
    val contentType: String,
    val download: Int = 0,
    val extension: String,
    @ColumnInfo(name = "file_url")
    val fileUrl: String,
    val isDownload: Int = 0
) {
    fun toData() = FileData(
        id,
        uploadPath,
        hashId,
        name,
        fileSize,
        contentType,
        download,
        extension,
        fileUrl,
        isDownload
    )
}