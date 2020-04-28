package com.android.example.motoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
class Table (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") val id: Int,
    @ColumnInfo(name = "latitude") val latitudeDB:String,
    @ColumnInfo(name = "longitude") val longitudeDB:String,
    @ColumnInfo(name = "time") val timeDB:Long
)








