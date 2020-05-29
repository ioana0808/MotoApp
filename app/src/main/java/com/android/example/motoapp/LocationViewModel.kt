package com.android.example.motoapp

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.overview_information.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode

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

    /**Summarization information */
//DATA PROCESS FUNCTION
 fun endRouteInfo(){viewModelScope.launch {
    val aux=auxEndRouteInfo()
    val distance=aux[0]
    val avSpeed=aux[1]
    val time=aux[2]
        //RecordingActivity.getMainInstance().location_output.text="$distance\n $avSpeed\n $time"
        OverviewInfoActivity.getOverviewInstance().text_overview.text="$distance\n$avSpeed\n$time"
        //OverviewInfoActivity().text_overview.text="$distance"

 } }

    private suspend fun auxEndRouteInfo():MutableList<String>{
        return withContext(Dispatchers.Default){

            val endRouteInfo=repository.endRouteInfo()
            val results=FloatArray(1)

            /**Need to add ROTATION VECTOR info*/
            //Global Results
            val itemList:MutableList<String> = ArrayList()
            val timeString:String
            val distanceString:String
            val averageSpeedString:String

            if(endRouteInfo.size>=2)
            {
                val lastIndex=endRouteInfo.lastIndex

                val startLatitude=endRouteInfo[0].latitudeDB
                val startLongitude=endRouteInfo[0].longitudeDB
                val endLatitude=endRouteInfo[lastIndex].latitudeDB
                val endLongitude=endRouteInfo[lastIndex].longitudeDB

                //Total distance in meters
                Location.distanceBetween(
                    startLatitude,
                    startLongitude,
                    endLatitude,
                    endLongitude,
                    results )
                val distanceInKm=(results[0]*0.001).toBigDecimal().setScale(2,RoundingMode.UP)
                distanceString="Distance: $distanceInKm km "


                //Total time in seconds
                val timeDifference=(endRouteInfo[lastIndex].timeDB-endRouteInfo[0].timeDB)
                //convert time in seconds into hh:mm:ss format
                val s=timeDifference%60
                val m=(timeDifference/60)%60
                val h=(timeDifference/(60*60))%24
                timeString=String.format("Time: %d:%02d:%02d",h,m,s)


                //Average Speed
                    //time in hours
                    val timeInHours=timeDifference*0.00027778
                    //distance  in km
                    val distanceMToKm=results[0]*0.001
                    //average speed  in km/h
                    val averageSpeedKmH=(distanceMToKm/timeInHours).toBigDecimal().setScale(2,RoundingMode.UP)
                    averageSpeedString= "Average Speed: $averageSpeedKmH km/h"
            }

            else
            {
                distanceString="Distance: 0 km"
                timeString="Time: 00:00:00"
                averageSpeedString="Average Speed: 0 km/h"
            }

            itemList.add(distanceString)
            itemList.add(averageSpeedString)
            itemList.add(timeString)
            return@withContext itemList
        }
    }

    /**Instant speed */
 //Calculate speed
fun last2records(){viewModelScope.launch {
  val aux=auxLast2records()
  RecordingActivity.getMainInstance().location_output.text=aux
} }

    private suspend fun auxLast2records():String{
        return withContext(Dispatchers.Default){

            val last2records = repository.last2records()
            val results=FloatArray(1)

            //Final global result
            val speedString:String

            //DISTANCE BETWEEN 2 POINTS AND SPEED
            if(last2records.size>=2){
                Location.distanceBetween(
                    last2records[0].latitudeDB,
                    last2records[0].longitudeDB,
                    last2records[1].latitudeDB,
                    last2records[1].longitudeDB,
                    results
                )
               //Time in seconds
                val timeDifference = last2records[0].timeDB-last2records[1].timeDB
                    //Time in hours
                    val timeHours=timeDifference*0.00027778
                    //Distance in km
                    val mToKm=results[0]*0.001

                    //Speed in km/h
                    val speedKmH=(mToKm/timeHours).toBigDecimal().setScale(2,RoundingMode.UP)
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