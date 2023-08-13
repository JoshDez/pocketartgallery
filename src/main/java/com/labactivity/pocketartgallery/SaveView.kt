package com.labactivity.pocketartgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.labactivity.pocketartgallery.databinding.ActivitySaveViewBinding
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class SaveView : AppCompatActivity() {
    private lateinit var binding: ActivitySaveViewBinding
    private lateinit var artworkDao: ArtworkDao

    private lateinit var title:String
    private lateinit var desc:String
    private var selectedBitmap: Bitmap? = null
    private var art:ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.artImgView.setImageResource(R.drawable.baseline_add_24)

        binding.artImgView.setOnClickListener {
            selectImage()
        }

        binding.btnBack.setOnClickListener(){
            this.finish()
        }

        binding.saveBtn.setOnClickListener(){
            //Initializing the variables
            //Artwork
            art = convertImageToByteArray()
            if(art==null){
                Toast.makeText(applicationContext, "Upload your Artwork", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //Title
            title = binding.titleEdtTxt.text.toString()
            if(title==""){
                Toast.makeText(applicationContext, "Please enter the Title", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //Description
            desc = binding.descEdtTxt.text.toString()
            if(desc==""){
                Toast.makeText(applicationContext, "Please enter the Description", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var day = binding.dayEdtTxt.text.toString()
            var month = binding.monthEdtTxt.text.toString()
            var year = binding.yearEdtTxt.text.toString()

            //Date Validation
            if(day == "" || month == "" || year == ""){
                Toast.makeText(applicationContext, "Fill the date of creation", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if(day.length < 2 || month.length < 2 || year.length < 4){
                Toast.makeText(applicationContext, "Enter the correct date format", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if(day.toInt() > 31 || month.toInt() > 12){
                Toast.makeText(applicationContext, "Invalid Date", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //Date
            var temp = "$year$month$day"
            var date = temp.toLong()


            lifecycleScope.launch{
                //Creating object
                val artwork = Artwork(art = art, title = title, desc = desc, date = date)

                //get database instance
                val database = DatabaseProvider.getDatabase(this@SaveView)

                //access database through Dao
                artworkDao = database.artworkDao()

                //Insert Reservation in the database
                artworkDao.insertArtwork(artwork)

                //going back to Main Activity
                intent = Intent(this@SaveView, MainActivity::class.java)
                startActivity(intent)
                this@SaveView.finish()
            }


        }

    }

    private val imageActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                selectedBitmap = imageUri?.let {
                    contentResolver.openInputStream(it)?.use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)
                    }
                }
                binding.artImgView.setImageBitmap(selectedBitmap)
            }


        }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        imageActivityResult.launch(intent)
    }

    private fun downsampleBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        val scaleFactor = Math.min(originalWidth / maxWidth.toFloat(), originalHeight / maxHeight.toFloat())
        return Bitmap.createScaledBitmap(bitmap, (originalWidth / scaleFactor).toInt(), (originalHeight / scaleFactor).toInt(), true)
    }


    private fun convertImageToByteArray(): ByteArray? {
        var imageByteArray: ByteArray? = null

        selectedBitmap?.let { bitmap ->
            val downscaledBitmap = downsampleBitmap(bitmap, 800, 800) // Adjust maxWidth and maxHeight as needed
            val outputStream = ByteArrayOutputStream()
            downscaledBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream) // Adjust quality as needed
            imageByteArray = outputStream.toByteArray()
        }
        return imageByteArray
    }

}