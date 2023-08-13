package com.labactivity.pocketartgallery
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "art_table")
data class Artwork(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val art: ByteArray?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "date") val date: Long
)
