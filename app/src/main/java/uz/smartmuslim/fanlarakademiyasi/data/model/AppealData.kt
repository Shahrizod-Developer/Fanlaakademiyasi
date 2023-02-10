package uz.smartmuslim.fanlarakademiyasi.data.model


import java.util.*

data class AppealData(
    val id: UUID,
    val useId: UUID,
    val type: AppealType,
    val fullName:String,
    val phoneNumber:String,
    val passportData:String,
    val address:String,
    val birthDate:String,
    val content: String,
    val recipient: Recipient,
    val createDate: String,
    val lastModifiedDate: String,
    val status: Int
)