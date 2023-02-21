package uz.smartmuslim.fanlarakademiyasi.data.remote.response

import com.google.gson.annotations.SerializedName
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.UserEntity
import java.util.*

data class UserResponse(
    val id: UUID,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("passport_sn")
    val passportSN: String,
    val address: String,
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("create_date")
    val createDate: Long,
) {
    fun toEntity() = UserEntity(
        id,
        fullName,
        phoneNumber,
        passportSN,
        address,
        birthDate,
        createDate,
    )
}