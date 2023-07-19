package uz.smartmuslim.fanlarakademiyasi.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.smartmuslim.fanlarakademiyasi.data.model.FileData


@Entity(tableName = "file")
data class FileEntity(
    @PrimaryKey(autoGenerate = false)
    val hashId: String,
    val name: String,
    val extension: String,
    val fileSize: Long,
    val data: String,
    @ColumnInfo("file_url")
    val fileUrl: String,
    val uploadPath: String,
    val download: Int = 0,
    val isDownload: Int = 0
) {
    fun toData() = FileData(
        hashId,
        name,
        extension,
        fileSize,
        data,
        fileUrl,
        uploadPath
    )
}