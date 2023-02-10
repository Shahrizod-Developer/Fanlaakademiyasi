package uz.smartmuslim.fanlarakademiyasi.data.remote.response

import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.AppealEntity
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealType
import uz.smartmuslim.fanlarakademiyasi.data.model.Recipient
import java.util.*


data class AppealResponse(
    val id: UUID,
    val userId: UUID,
    val fullname: String,
    val phoneNumber: String,
    val passportSN: String,
    val address: String,
    val birthDate: String,
    val messageType: AppealType,
    val content: String,
    val recipient: Recipient,
    val createdDate: String,
    val isComplete: Boolean,
    val lastModifiedDate: String,
) {
    fun toEntity() =
        AppealEntity(
            id,
            userId,
            fullname,
            phoneNumber,
            passportSN,
            address,
            messageType,
            recipient,
            birthDate,
            createdDate,
            content,
            lastModifiedDate
        )
}
