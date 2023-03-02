package com.example.woodpeakeradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.example.woodpeakeradmin.databinding.ActivityMainTwoBinding

class MainActivityTwo : AppCompatActivity() {
    lateinit var binding:ActivityMainTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id=binding.llayout

        Log.d("TAG",binding.llayout.id.toString())
        Log.d("TAG",id.toString())

    }
}