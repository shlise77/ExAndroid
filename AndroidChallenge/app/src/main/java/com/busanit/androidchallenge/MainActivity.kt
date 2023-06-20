package com.busanit.androidchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.busanit.androidchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar = binding.toolbar
        setSupportActionBar(toolbar)
    }
}


