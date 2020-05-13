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

    //START AND END LATITUDE AND LONGITUDE TO DETERMINE  DISTANCE BETWEEN(end-start=distance)
        //last location
    @Query("SELECT  * from location_table ORDER BY id DESC LIMIT 1")
     fun  endLocation():LiveData<Table>

        //second last location
    @Query("SELECT  * from location_table ORDER BY id DESC LIMIT 2 OFFSET 1")
    fun  startLocation():LiveData<Table>

   }