package com.android.example.motoapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(){

   private lateinit var mDrawerLayout:DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var locationViewModel:LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        locationViewModel= ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.deleteAll()

        StartRouteBtn?.setOnClickListener {
            startActivity(Intent(this, RecordingActivity::class.java))
            finish()
        }

        MainLogoutBtn?.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }

        mDrawerLayout=findViewById(R.id.drawerLayout)
        mToggle= ActionBarDrawerToggle(this,mDrawerLayout,R.string.register,R.string.routes)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(mToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}