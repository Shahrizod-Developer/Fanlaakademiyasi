package uz.smartmuslim.fanlarakademiyasi.data.local.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(data: T)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: List<T>)

    @Update
    fun update(data: T)

    @Update
    fun update(data: List<T>)
}