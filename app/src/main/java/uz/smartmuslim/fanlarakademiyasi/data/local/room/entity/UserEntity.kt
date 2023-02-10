package uz.smartmuslim.fanlarakademiyasi.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.smartmuslim.fanlarakademiyasi.data.model.UserData
import java.util.UUID


@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: UUID,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "passport_sn")
    val passportSN: String,
    val address: String,
    @ColumnInfo(name = "birth_date")
    val birthDate: String,
    @ColumnInfo(name = "create_date")
    val createDate: Long,
    @ColumnInfo(name = "last_modified_date")
    val lastModifiedDate: Long
) {
    fun toData() = UserData(
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