package uz.smartmuslim.fanlarakademiyasi.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealType
import uz.smartmuslim.fanlarakademiyasi.data.model.Recipient
import java.util.UUID

@Entity(tableName = "appeal")
data class AppealEntity(
    @PrimaryKey
    val id: UUID,
    @ColumnInfo(name = "user_id")
    val useId: UUID,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "passport_data")
    val passportData: String,
    val address: String,
    val type: AppealType,
    val recipient: Recipient,
    @ColumnInfo(name = "birth_date")
    val birthDate: String,
    @ColumnInfo(name = "create_date")
    val createDate: String,
    val content: String,
    @ColumnInfo(name = "last_modified_date")
    val lastModifiedDate: String,
    val answer: String = "",
    val status: Int = 0
) {
    fun toData() =
        AppealData(
            id,
            useId,
            type,
            fullName,
            phoneNumber,
            passportData,
            address,
            birthDate,
            content,
            recipient,
            createDate,
            lastModifiedDate,
            status
        )
}