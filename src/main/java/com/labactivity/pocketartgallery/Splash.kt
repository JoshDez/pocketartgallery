package com.labactivity.pocketartgallery

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Use your splash activity layout

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java) // Replace with your main activity
            startActivity(intent)
            finish() // Finish the splash activity to prevent coming back to it from back navigation
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}