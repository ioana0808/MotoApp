package com.android.example.motoapp
import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor

import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_record.*


class RecordingActivity : AppCompatActivity(),SensorEventListener{

    private lateinit var locationRequest: LocationRequest
    private lateinit var  fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationViewModel:LocationViewModel

     private lateinit var  sensorManager: SensorManager
      lateinit var rotationVector:String


    companion object{
        var instance:RecordingActivity?=null

        fun getMainInstance():RecordingActivity{
            return instance!!
        }
    }

    fun updateTextView(value:String){
        this@RecordingActivity.runOnUiThread{
            //location_output.text=value
        }
       // location_output.text=locationViewModel.last2records()
    }


//Function for inserting location into Room
    fun insertDB(id:Int, latitude:Double, longitude:Double, rotation: String, time:Long){
      locationViewModel.insert(Table(id,latitude,longitude,rotation,time))
    }


/*ON CREATE METHOD*/
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)



//POSITION SENSOR
    sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR),
            SensorManager.SENSOR_DELAY_NORMAL
        )

//ROOM-UI
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerview)
        val adapter=DatabaseListAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

  //update ViewModel for db
        locationViewModel=ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.allLocations.observe(this, Observer{info->
            info?.let{adapter.setInfo(it)
            }
        })

        instance=this
    locationViewModel.last2records()
//Permissions for getting location from GPS
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    updateLocation()
                    }
                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@RecordingActivity,"You must accept this permission",Toast.LENGTH_SHORT).show()
                }
            }).check()

//Stop recording route button
        stopRecordBtn.setOnClickListener{
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

//unregister the sensor
    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

//LOCATION:Updates location
    private fun updateLocation() {
        buildLocationRequest()

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED)
            return
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,getPendingIntent())
    }

    private fun getPendingIntent(): PendingIntent? {
        val intent= Intent(this@RecordingActivity,MyLocationService::class.java)
        intent.action = MyLocationService.ACTION_PROCESS_UPDATE
        return PendingIntent.getBroadcast(this@RecordingActivity,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildLocationRequest() {
        locationRequest= LocationRequest()
        locationRequest.priority= LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=1000
        locationRequest.fastestInterval=1000
        locationRequest.smallestDisplacement=10f
    }


//Position sensor methods
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

//Set sensor's xyz values to rotationVector,where x=values[0], y=values[1], z=values[2]
    override fun onSensorChanged(event: SensorEvent?) {
        rotationVector= event!!.values.clone().toString()
       //rotation_output.text =rotationVector

//    rotation_output.text=          "x= "+event.values[0].toString()+
//                         " y= "+event.values[1].toString()+
//                         " z= "+event.values[2].toString()
    }


}
