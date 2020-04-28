package com.android.example.motoapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.example.motoapp.RecordingActivity.Companion.getMainInstance
import com.google.android.gms.location.LocationResult

class MyLocationService : BroadcastReceiver() {

        companion object {
            const val ACTION_PROCESS_UPDATE="com.android.example.motoapp.UPDATE_LOCATION"
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onReceive(context: Context?, intent:Intent?){
            if(intent!=null){
                val action = intent.action
                if(action.equals(ACTION_PROCESS_UPDATE))
                {
                    val result = LocationResult.extractResult(intent)
                    if(result!=null)
                    {
                        val location = result.lastLocation

                        val locationString=StringBuilder(location.latitude.toString())
                            .append("/").append(location.longitude).toString()

                        try{
                           getMainInstance().updateTextView(locationString)
                            getMainInstance().insertDB(4,locationString)


                        }catch (e:Exception){
                            //if app in killed mode
                            Toast.makeText(context,locationString,Toast.LENGTH_SHORT).show()
                        }

                    }

                }
            }


        }
}

