package uz.smartmuslim.fanlarakademiyasi.data.model

import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.UserEntity
import java.util.*

data class UserData(
    val id: UUID,
    val fullName: String,
    val phoneNumber: String,
    val passportSN: String,
    val address: String,
    val birthDate: String,
    val createDate: Long,
    val lastModifiedDate: Long
) {
    fun toEntity() = UserEntity(
        id,
        fullName,
        phoneNumber,
        passportSN,
        address,
        birthDate,
        createDate,
        lastModifiedDate
    )
}