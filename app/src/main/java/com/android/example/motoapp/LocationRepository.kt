package com.android.example.motoapp

import android.location.Location.distanceBetween
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class LocationRepository(private val tableDao:TableDao ) {

    val allLocations:LiveData<List<Table>> = tableDao.getAllFromTable()
    //START-END LATITUDE & LONGITUDE

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
}