package uz.smartmuslim.fanlarakademiyasi.data.local.room.dao

import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    fun insert(data: T)

    @Insert
    fun insert(data: List<T>)

    @Update
    fun update(data: T)

    @Update
    fun update(data: List<T>)
}