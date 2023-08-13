package com.labactivity.pocketartgallery

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Artwork::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artworkDao() : ArtworkDao
}