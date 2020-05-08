package com.android.example.motoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application):AndroidViewModel(application) {

    private val repository:LocationRepository

    val allLocations:LiveData<List<Table>>
//START&END LATITUDE&LONGITUDE
    val startLatitude:LiveData<Table>
    val startLongitude:LiveData<Table>
    val endLatitude:LiveData<Table>
    val endLongitude:LiveData<Table>


    init {
        val tablesDao=LocationRoomDatabase.getDatabase(application,viewModelScope).tableDao()
        repository= LocationRepository(tablesDao)
        allLocations=repository.allLocations
//START&END LATITUDE&LONGITUDE
        startLatitude=repository.startLatitude
        startLongitude=repository.startLongitude
        endLatitude=repository.endLatitude
        endLongitude=repository.endLongitude
    }

    //Insert method on back thread
    fun insert(table: Table) =viewModelScope.launch(Dispatchers.IO) {
        repository.insert(table)
    }
//START&END LATITUDE&LONGITUDE
    fun startLatitude()=viewModelScope.launch(Dispatchers.IO){
        startLatitude
   }
    fun startLongitude()=viewModelScope.launch(Dispatchers.IO){
        startLongitude
    }
    fun endLatitude()=viewModelScope.launch(Dispatchers.IO){
        startLatitude
    }
    fun endLongitude()=viewModelScope.launch(Dispatchers.IO){
        startLongitude
    }
}