package com.android.example.motoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Table::class),version =1 ,exportSchema = false)
public abstract class LocationRoomDatabase:RoomDatabase() {

    abstract fun  tableDao():TableDao

private class LocationDatabaseCallback(
    private val scope: CoroutineScope
):RoomDatabase.Callback(){

    //delete all content and repopulate the database whenever the app is started
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        INSTANCE?.let { database ->
            scope.launch {
                populateDatabase(database.tableDao())
            }
        }
    }
    suspend fun populateDatabase(tableDao:TableDao){
        //tableDao.deleteAll()

        //val latitudeDB=Table(1,"")
       //tableDao.insert(latitudeDB)
    }
}

companion object{

        @Volatile
        private var INSTANCE:LocationRoomDatabase?=null

        fun getDatabase(
            context: Context,
            scope:CoroutineScope
        ):LocationRoomDatabase{

           return INSTANCE?:synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    LocationRoomDatabase::class.java,
                    "location_database"
                )
                    .addCallback(LocationDatabaseCallback(scope))
                    .build()
                INSTANCE=instance
                instance
            }
        }
    }

}