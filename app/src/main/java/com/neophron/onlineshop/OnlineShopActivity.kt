package com.neophron.onlineshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neophron.mylibrary.ktx.getColor

class OnlineShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(
            theme.getColor(com.google.android.material.R.attr.backgroundColor)
        )
    }
}