package com.android.example.motoapp

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class LocationViewModel(application: Application):AndroidViewModel(application) {

    private val repository:LocationRepository

    val allLocations:LiveData<List<Table>>
//START&END LATITUDE&LONGITUDE
   // val last2records:Float



    init {
        val tablesDao=LocationRoomDatabase.getDatabase(application,viewModelScope).tableDao()
        repository= LocationRepository(tablesDao)
        allLocations=repository.allLocations
//START&END LATITUDE&LONGITUDE
       //
        }

    //Insert method on back thread
    fun insert(table: Table) =viewModelScope.launch(Dispatchers.IO) {
        repository.insert(table)
    }


//DATA PROCESS FUNCTION

//    fun last2recordsAsync()=viewModelScope.launch(Dispatchers.IO) {
//     repository.last2record()
//}

    fun last2records()
    {
        viewModelScope.launch(Dispatchers.IO) {

                val last2records = repository.last2records()
                lateinit var results: FloatArray
                val timeDifference = last2records[1].timeDB - last2records[0].timeDB

                Location.distanceBetween(
                    last2records[0].latitudeDB,
                    last2records[0].longitudeDB,
                    last2records[1].latitudeDB,
                    last2records[1].longitudeDB,
                    results
                )
        }
    }

}