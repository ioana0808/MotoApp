package com.android.example.motoapp

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "location_table")
class Table (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") val id: Int,
    @ColumnInfo(name = "latitude") val latitudeDB:Double,
    @ColumnInfo(name = "longitude") val longitudeDB:Double,
    @ColumnInfo(name="rotation")val rotationDB:String,
    @ColumnInfo(name = "time") val timeDB:Int
)








