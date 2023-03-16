package uz.smartmuslim.fanlarakademiyasi.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.FileEntity

@Parcelize
data class FileData(
    val id: String,
    val uploadPath: String,
    val hashId: String,
    val name: String,
    val fileSize: Long,
    val contentType: String,
    val download: Int,
    val extension: String,
    val fileUrl: String,
    val isDownload: Int
) : Parcelable {
    fun toEntity() = FileEntity(
        id, uploadPath, hashId, name, fileSize, contentType, download, extension, fileUrl, isDownload
    )
}