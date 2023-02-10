package uz.smartmuslim.fanlarakademiyasi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.AppealEntity


@Dao
interface AppealDao : BaseDao<AppealEntity> {

    @Query("Select * From appeal Where status = 0")
    fun getAllUnreadAppeals(): Flow<List<AppealEntity>>

    @Query("Select * From appeal Where status = 1")
    fun getAllReadAppeals(): Flow<List<AppealEntity>>

    @Query("Select * From appeal Where status = 3")
    fun getAllAnsweredAppeals(): Flow<List<AppealEntity>>
}