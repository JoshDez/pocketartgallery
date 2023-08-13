package com.labactivity.pocketartgallery

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.labactivity.pocketartgallery.databinding.ItemLayoutBinding
import com.bumptech.glide.Glide

class ItemViewHolder(private val context: Context, private val binding: ItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var title: String
    private lateinit var desc: String
    private lateinit var date: String
    private var id: Long = 0
    private var art: ByteArray? = null
    private var artwork:Bitmap? = null

    init {
        binding.root.setOnClickListener(this)
    }

    private fun getArt(): ByteArray? {
        return this.art
    }
    private fun getId(): Long {
        return this.id
    }

    private fun getTitle(): String {
        return this.title
    }

    private fun getDate(): String {
        return this.date
    }

    private fun getDesc(): String {
        return this.desc
    }

    fun bind(item: Item) {
        this.id = item.id
        this.title = item.title
        this.desc = item.desc
        this.date = "${item.date} "
        this.art = item.art

        // parsing date
        date = "${date[6]}${date[7]}/${date[4]}${date[5]}/${date[0]}${date[1]}${date[2]}${date[3]}"


        //binding components
        val imageByteArray: ByteArray? = getArt()// Your image byte array
        if(imageByteArray != null){
            Glide.with(context)
                .asBitmap()
                .load(imageByteArray)
                .into(binding.artworkIV)
        } else {
            binding.artworkIV.setImageResource(R.drawable.baseline_add_24)
        }


        binding.nameDateTV.text = "${getTitle()}\n\n${getDate()}"
    }

    override fun onClick(v: View?) {
        val intent = Intent(context, Preview::class.java)
        intent.putExtra("id", getId())
        intent.putExtra("title", getTitle())
        intent.putExtra("date", getDate())
        intent.putExtra("desc", getDesc())
        context.startActivity(intent)
    }
}
