package com.android.example.motoapp

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.activity_record.*
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

  fun last2records(){viewModelScope.launch {
      val aux=auxFun()
      RecordingActivity.getMainInstance().location_output.text=aux
  } }

    suspend fun auxFun():String{
        return withContext(Dispatchers.Default){
            val last2records = repository.last2records()
            val results=FloatArray(3)
             val resultList= arrayListOf<String>()

            val timeDifference = last2records[1].timeDB - last2records[0].timeDB

             Location.distanceBetween(
                last2records[0].latitudeDB,
                last2records[0].longitudeDB,
                last2records[1].latitudeDB,
                last2records[1].longitudeDB,
                results
            )
            val distanceM= results[0].toString()
            //val speed= distanceM.div(timeDifference)
            val distanceMString=distanceM.toString()
            //val speedString=speed.toString()

                resultList.toMutableList().add(0,distanceMString)
                //resultList.toMutableList().add(1,speedString)

            return@withContext distanceM
        }
    }
//= viewModelScope.launch(Dispatchers.IO)


}