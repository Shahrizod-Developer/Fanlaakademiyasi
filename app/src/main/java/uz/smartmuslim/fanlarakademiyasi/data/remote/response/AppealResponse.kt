package uz.smartmuslim.fanlarakademiyasi.data.remote.response

import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.AppealEntity
import java.util.*

data class AppealResponse(
    var id: String,
    var userId: String,
    var fullname: String,
    var phoneNumber: String,
    var passportSN: String,
    var address: String,
    var birthDate: String,
    var messageType: String,
    var content: String,
    var recipient: String,
    var createdDate: Long,
    var answer: String,
    var answeredTime: Long,
    val appealNumber: Int,
    var isComplete: Boolean,
) {
    fun toEntity() =
        AppealEntity(
            id,
            userId,
            fullname,
            phoneNumber,
            passportSN,
            address,
            birthDate,
            messageType,
            content,
            recipient,
            createdDate,
            isComplete,
            answer,
            answeredTime,
            appealNumber,
            status = 0,
        )
}
