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

    //START AND END LATITUDE AND LONGITUDE TO DETERMINE  DISTANCE BETWEEN
    @Query("SELECT  latitude from location_table ORDER BY id DESC LIMIT 1")
     fun  startLatitude():LiveData<Double>

    @Query("SELECT  longitude from location_table ORDER BY id DESC LIMIT 1")
    fun  startLongitude():LiveData<Table>

    @Query("SELECT  latitude from location_table WHERE (SELECT MAX(id)-1)")
     fun  endLatitude():LiveData<Table>

    @Query("SELECT  longitude from location_table WHERE (SELECT MAX(id)-1)")
    fun  endLongitude():LiveData<Table>

   }