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
    val startLocation:LiveData<Table>
    val endLocation:LiveData<Table>


    init {
        val tablesDao=LocationRoomDatabase.getDatabase(application,viewModelScope).tableDao()
        repository= LocationRepository(tablesDao)
        allLocations=repository.allLocations
//START&END LATITUDE&LONGITUDE
        startLocation=repository.startLocation
        endLocation=repository.endLocation
        }

    //Insert method on back thread
    fun insert(table: Table) =viewModelScope.launch(Dispatchers.IO) {
        repository.insert(table)
    }
//START&END LATITUDE&LONGITUDE
    fun startLocation()=viewModelScope.launch(Dispatchers.IO){
    startLocation
   }
    fun endLocation()=viewModelScope.launch(Dispatchers.IO){
        endLocation
    }
}