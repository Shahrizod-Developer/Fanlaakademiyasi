package uz.smartmuslim.fanlarakademiyasi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.UserEntity

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("Select * From user ")
    fun getAllUsers(): Flow<List<UserEntity>>
}