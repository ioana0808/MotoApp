package com.android.example.motoapp

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class LocationRepository(private val tableDao:TableDao ) {

    val allLocations:LiveData<List<Table>> = tableDao.getAllFromTable()
    //START-END LATITUDE & LONGITUDE
    val startLocation:LiveData<Table> = tableDao.startLocation()
    val endLocation:LiveData<Table> = tableDao.endLocation()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(table: Table) {
        tableDao.insert(table)
    }
}