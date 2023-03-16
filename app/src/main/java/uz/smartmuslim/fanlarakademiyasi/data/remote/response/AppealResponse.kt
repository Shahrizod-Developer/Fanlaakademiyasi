package uz.smartmuslim.fanlarakademiyasi.data.remote.response

import com.google.gson.annotations.SerializedName
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.AppealEntity
import java.util.*

data class AppealResponse(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("userId")
    var userId: String? = null,
    @SerializedName("fullname")
    var fullname: String? = null,
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,
    @SerializedName("passportSN")
    var passportSN: String? = null,
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("birthDate")
    var birthDate: String? = null,
    @SerializedName("messageType")
    var messageType: String? = null,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("recipient")
    var recipient: String? = null,
    @SerializedName("createdDate")
    var createdDate: String? = null,
    @SerializedName("isComplete")
    var isComplete: String? = null,
    @SerializedName("answer")
    var answer: String? = null,
    @SerializedName("answeredTime")
    var answeredTime: String? = null,
    @SerializedName("appealNumber")
    var appealNumber: String? = null,
    @SerializedName("userMessageFileHashId")
    var userMessageFileHashId: String? = null,
    @SerializedName("adminMessageFileHashId")
    var adminMessageFileHashId: String? = null,
    @SerializedName("userLang")
    var userLang: String? = null
) {
    fun toEntity(): AppealEntity {

        return AppealEntity(
            id!!,
            userId!!,
            fullname!!,
            phoneNumber!!,
            passportSN!!,
            address!!,
            birthDate!!,
            messageType!!,
            content!!,
            recipient!!,
            createdDate!!.toLong(),
            isComplete.toBoolean(),
            answer!!,
            answeredTime!!.toLong(),
            appealNumber!!.toInt(),
            status = 0,
            userMessageFileHashId!!,
            "",
            adminMessageFileHashId!!,
            "",
            userLang!!
        )
    }
}
