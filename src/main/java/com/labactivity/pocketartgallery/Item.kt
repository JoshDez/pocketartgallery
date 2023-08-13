package com.labactivity.pocketartgallery

data class Item(
    val id:Long,
    val art:ByteArray?,
    val title:String,
    val desc:String,
    val date:Long
)
