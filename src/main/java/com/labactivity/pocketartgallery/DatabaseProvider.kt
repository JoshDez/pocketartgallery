package com.labactivity.pocketartgallery
import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase{
        return instance?: synchronized(this){
            instance?:buildDatabase(context).also {instance = it}
        }
    }

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,"my-database"
        ).build()
    }

}