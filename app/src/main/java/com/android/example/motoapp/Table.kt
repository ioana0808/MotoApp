package com.android.example.motoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
class Table (
    @PrimaryKey
    @ColumnInfo(name = "latitude")val latitudeDB:String
   // @ColumnInfo(name = "longitude")val longitudeDB:Float


)

