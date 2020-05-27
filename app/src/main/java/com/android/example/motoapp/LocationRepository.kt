package com.android.example.motoapp

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
    suspend fun deleteAll(){
        tableDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun last2records():List<Table>{
        return tableDao.last2records()

    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun endRouteInfo():List<Table>{
        return tableDao.endRouteInfo()

    }

}