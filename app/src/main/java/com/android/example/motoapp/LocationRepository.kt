package com.android.example.motoapp

import android.location.Location.distanceBetween
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class LocationRepository(private val tableDao:TableDao ) {

    val allLocations:LiveData<List<Table>> = tableDao.getAllFromTable()
    //START-END LATITUDE & LONGITUDE
//    val last2records:List<Table> = tableDao.last2records()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(table: Table) {
        tableDao.insert(table)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun last2records():List<Table>{
        return tableDao.last2records()
    }

//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun last2records():Float{
//        lateinit var results:FloatArray
//
//
//            val timeDifference=last2records[1].timeDB-last2records[0].timeDB
//            distanceBetween(
//               last2records[0].latitudeDB,
//               last2records[0].longitudeDB,
//               last2records[1].latitudeDB,
//               last2records[1].longitudeDB,
//               results)
//
//        return results[0]
//    }
}