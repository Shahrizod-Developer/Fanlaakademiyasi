package uz.smartmuslim.fanlarakademiyasi.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.FileEntity

@Parcelize
data class FileData(
    val hashId: String = "",
    val name: String = "",
    val extension: String = "",
    val fileSize: Long = 0L,
    val data: String = "",
    val fileUrl: String = "",
    val uploadPath: String = "",
    val download: Int = 0,
    val isDownload: Int = 0
) : Parcelable {
    fun toEntity() = FileEntity(
        hashId, name, extension, fileSize, data, fileUrl, uploadPath
    )
}