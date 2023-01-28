package com.example.woodpeakeradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeakeradmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun orders(view: View) {
        startActivity(Intent(applicationContext, Orders::class.java))
    }
    fun allAds(view: View) {
        startActivity(Intent(applicationContext, AllAds::class.java))
    }

    fun newAd(view: View) {
        startActivity(Intent(applicationContext, NewAd::class.java))
    }

}