package com.busanit.androidchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.busanit.androidchallenge.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.saveToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_check,menu)
        return super.onCreateOptionsMenu(menu)
    }
}