package uz.smartmuslim.fanlarakademiyasi.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.AppealEntity
import java.util.*

@Parcelize
data class AppealData(
    val id: String,
    val useId: String,
    val type: String,
    val fullName: String,
    val phoneNumber: String,
    val passportData: String,
    val address: String,
    val birthDate: String,
    val content: String,
    val recipient: String,
    val createDate: Long,
    var answer: String,
    var answeredTime: Long,
    val appealNumber: Int,
    var status: Int,
    val userMessageFileHashId: String,
    val adminMessageFileHashId: String,
    var userMessageFileName: String,
    var adminMessageFileName: String,
    var userLang: String
) : Parcelable {
    fun toEntity() = AppealEntity(
        id,
        useId,
        fullName,
        phoneNumber,
        passportData,
        address,
        birthDate,
        type,
        content,
        recipient,
        createDate,
        true,
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