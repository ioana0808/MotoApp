package com.android.example.motoapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TableDao
{
    @Query("SELECT * from location_table")
     fun  getAllFromTable(): LiveData<List<Table>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(table: Table)
//Delete all in Dao
    @Query("DELETE FROM location_table")
    suspend fun deleteAll()

    //START AND END LATITUDE AND LONGITUDE TO DETERMINE  DISTANCE BETWEEN(end-start=distance)

    @Query("SELECT * from location_table ORDER BY id DESC LIMIT 2")
     fun  last2records():List<Table>

    @Query("SELECT * from location_table")
    fun endRouteInfo():List<Table>



   }