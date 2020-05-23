package com.android.example.motoapp

import android.app.Application
import android.location.Location
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.*
import java.lang.Runnable
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

class LocationViewModel(application: Application):AndroidViewModel(application) {

    private val repository:LocationRepository
    val allLocations:LiveData<List<Table>>


    init {
        val tablesDao=LocationRoomDatabase.getDatabase(application,viewModelScope).tableDao()
        repository= LocationRepository(tablesDao)
        allLocations=repository.allLocations
        }

    //Insert method on back thread
    fun insert(table: Table) =viewModelScope.launch(Dispatchers.IO) {
        repository.insert(table)
    }
//Delete ROOM on STOP button
    fun deleteAll(){viewModelScope.launch {
      repository.deleteAll()
}}

//DATA PROCESS FUNCTION

  fun last2records(){viewModelScope.launch {
      val aux=auxFun()
      RecordingActivity.getMainInstance().location_output.text=aux
  } }




    suspend fun auxFun():String{
        return withContext(Dispatchers.Default){

            val last2records = repository.last2records()
            val speedString:String
            val results=FloatArray(1)
            val resultList:List<String>

            //CALCUL DISTANTA DINTRE 2 PUNCTE SI CALCUL VITEZA
            if(last2records.size>=2){
                Location.distanceBetween(
                    last2records[0].latitudeDB,
                    last2records[0].longitudeDB,
                    last2records[1].latitudeDB,
                    last2records[1].longitudeDB,
                    results
                )
               //diferenta de timp in secunde
                val timeDifference = last2records[0].timeDB-last2records[1].timeDB
                //timp in ore
                val timeHours=timeDifference*0.00027778
                //distanta in km
                val mToKm=results[0]*0.001
                //viteza in km/h
                val speedKmH=(mToKm/timeHours).roundToInt()
                speedString= "$speedKmH km/h"
            }
           else
            {
                speedString="0 km/h"
            }

            return@withContext speedString
        }
    }



}