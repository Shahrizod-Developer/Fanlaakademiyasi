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
    val id: String,
    @ColumnInfo(name = "user_id")
    val useId: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "passport_data")
    val passportData: String,
    val address: String,
    @ColumnInfo(name = "birth_date")
    val birthDate: String,
    val type: String,
    val content: String,
    val recipient: String,
    @ColumnInfo(name = "create_date")
    val createDate: Long,
    @ColumnInfo(name = "is_complete")
    val isComplete: Boolean,
    val answer: String,
    val answeredTime: Long,
    val appealNumber: Int,
    var status: Int,
    @ColumnInfo(name = "user_message_file_hash_id")
    val userMessageFileHashId: String,
    @ColumnInfo(name = "user_message_file_name")
    val userMessageFileName: String,
    @ColumnInfo(name = "admin_message_file_hash_id")
    val adminMessageFileHashId: String,
    @ColumnInfo(name = "admin_message_file_name")
    val adminMessageFileName: String,
    @ColumnInfo(name = "user_lang")
    val userLang: String
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
            answer,
            answeredTime,
            appealNumber,
            status,
            userMessageFileHashId,
            adminMessageFileHashId,
            userMessageFileName,
            adminMessageFileName,
            userLang
        )
}