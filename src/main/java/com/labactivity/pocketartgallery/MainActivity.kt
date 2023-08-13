package com.labactivity.pocketartgallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.labactivity.pocketartgallery.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var artworkDao: ArtworkDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener(){
            val intent = Intent(this, SaveView::class.java)
            startActivity(intent)
        }
        loadImages()
    }

    private fun loadImages(){
        recyclerView = binding.myList
        recyclerView.layoutManager = LinearLayoutManager(this)


        lifecycleScope.launch{
            //initialize database
            val database = DatabaseProvider.getDatabase(this@MainActivity)

            //initialize artworkDao
            artworkDao = database.artworkDao()

            //retrieving artwork list from database
            val list = artworkDao.getAllArtwork()

            //initializing Item list
            val artList:ArrayList<Item> = ArrayList()
            for(item in list){
                val art = Item(id = item.id, art = item.art,title = item.title, desc = item.desc, date = item.date)
                artList.add(art)
            }

            recyclerView.adapter = ItemAdapter(this@MainActivity, artList)

        }
    }
}