package com.android.example.motoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
class Table (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "latitude")var latitudeDB:String
)




