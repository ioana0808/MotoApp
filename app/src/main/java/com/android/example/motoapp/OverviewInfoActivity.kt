package com.android.example.motoapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.overview_information.*

class OverviewInfoActivity:AppCompatActivity() {

    private lateinit var locationViewModel:LocationViewModel

    companion object{
        private var instance: OverviewInfoActivity? =null
        fun getOverviewInstance(): OverviewInfoActivity{
            return instance!!
        }
    }
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overview_information)
        locationViewModel= ViewModelProvider(this).get(LocationViewModel::class.java)
        instance=this

            /**Call method for Summarization information */
             locationViewModel.endRouteInfo()

        button_overview.setOnClickListener {
            /**Delete all from Room*/
            locationViewModel.deleteAll()
            /**Start Dashboard*/
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}