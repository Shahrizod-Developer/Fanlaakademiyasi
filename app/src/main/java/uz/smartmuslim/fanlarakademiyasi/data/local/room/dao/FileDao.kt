package uz.smartmuslim.fanlarakademiyasi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.FileEntity


@Dao
interface FileDao : BaseDao<FileEntity> {

    @Query("Select * From file Where hash_id = :hashId")
    fun getFileByHashId(hashId: String): Flow<FileEntity>
}