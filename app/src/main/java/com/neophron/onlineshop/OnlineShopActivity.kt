package com.neophron.onlineshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OnlineShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawableResource(R.drawable.day_night)
    }
}