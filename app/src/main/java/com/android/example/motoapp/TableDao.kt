package com.android.example.motoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TableDao
{
    @Query("SELECT * from location_table")
     fun  getAllFromTable(): LiveData<List<Table>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(table: Table)

    @Query("DELETE FROM location_table")
    suspend fun deleteAll()
}