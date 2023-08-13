package com.labactivity.pocketartgallery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.labactivity.pocketartgallery.databinding.ActivityPreviewBinding
import kotlinx.coroutines.launch

class Preview : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private lateinit var artworkDao:ArtworkDao

    private var title:String? = ""
    private var desc:String? = ""
    private var date:String? = ""
    private var id:Long? = 0
    private var art:ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idDefault:Long = 0

        // Retrieving data from previous activity
        val passedId = intent.getLongExtra("id", idDefault)
        title = intent.getStringExtra("title")
        date = intent.getStringExtra("date")
        desc = intent.getStringExtra("desc")

        //back button
        binding.backBtn.setOnClickListener(){
            this.finish()
        }

        //confirm button
        binding.confirmBtn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        //delete button
        binding.deleteBtn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
            lifecycleScope.launch{
                val database = DatabaseProvider.getDatabase(this@Preview)
                artworkDao = database.artworkDao()
                artworkDao.deleteArtworkById(passedId)
            }
        }

        // Retrieving image from database
        lifecycleScope.launch{

            //get database instance
            val database = DatabaseProvider.getDatabase(this@Preview)

            //access database through Dao
            artworkDao = database.artworkDao()

            //get art from database
            val artFromDB = artworkDao.getArtworkById(passedId)
            if(artFromDB != null){
                art = artFromDB
            }

            //Bind Views
            setImage(art)
            binding.titleTV.text = title
            binding.dateTV.text = date
            binding.descTV.text = desc

        }

    }
    private fun setImage(art:ByteArray?){
        // Load image using Glide
        Glide.with(this)
            .asBitmap()
            .load(art)
            .into(binding.artworkIV)
    }
}
