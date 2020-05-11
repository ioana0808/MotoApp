package com.android.example.motoapp

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class LocationRepository(private val tableDao:TableDao ) {

    val allLocations:LiveData<List<Table>> = tableDao.getAllFromTable()
    //START-END LATITUDE & LONGITUDE
    val startLatitude:LiveData<Table> = tableDao.startLatitude()
    val startLongitude:LiveData<Table> = tableDao.startLongitude()
    val endLatitude:LiveData<Table> = tableDao.endLatitude()
    val endLongitude:LiveData<Table> = tableDao.endLongitude()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(table: Table) {
        tableDao.insert(table)
    }
}