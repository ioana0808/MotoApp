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
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.timerTask

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
      val timer=Timer()
      val aux=auxFun()
      timer.scheduleAtFixedRate(timerTask{
          RecordingActivity.getMainInstance().location_output.text=aux
      },0,60000)//sa se execute o data la 1 minut fara intarziere

      //RecordingActivity.getMainInstance().location_output.text=aux

  } }

    suspend fun auxFun():String{
        return withContext(Dispatchers.Default){
            val last2records = repository.last2records()
            val distanceM:String
            val results=FloatArray(1)
             val resultList:List<String>

            //CALCUL DISTANTA DINTRE 2 PUNCTE SI CALCUL VITEZA

             Location.distanceBetween(
                last2records[0].latitudeDB,
                last2records[0].longitudeDB,
                last2records[1].latitudeDB,
                last2records[1].longitudeDB,
                results
            )
            //PT testare
//            Location.distanceBetween(
//                13.40,
//                1.43,
//               13.43,
//                1.40,
//                results
//            )
            val timeDifference = last2records[0].timeDB - last2records[1].timeDB
            val speed= results[0]/(timeDifference)
            //distanta ca si text
            if(results[0]<1000)
                if (results[0]<1)
                   distanceM= String.format(Locale.US,"%dm",1)
                else
                    distanceM= String.format(Locale.US,"%dm",Math.round(results[0]))
            else if (results[0]>10000)
                if(results[0]<1000000)
                    distanceM= String.format(Locale.US,"%dkm",Math.round(results[0]/1000))
                else distanceM="REST"
            else distanceM=String.format(Locale.US,"%2fkm",results[0]/1000)

            //Need to adjust format!!!!! km/h !!! put everything in a list and print both from that list into UI
            val speedString=speed.toString()



            return@withContext distanceM
        }
    }



}