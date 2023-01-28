package com.example.woodpeakeradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.woodpeakeradmin.databinding.ActivityNewAdBinding
import com.example.woodpeakeradmin.databinding.CustomViewImageBinding
import com.example.woodpeakeradmin.databinding.CustomviewFeaturesBinding

class NewAd : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var binding:ActivityNewAdBinding
    var featureArray=ArrayList<CustomviewFeaturesBinding>()
    var imageArray=ArrayList<CustomViewImageBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addFeature.setOnClickListener(View.OnClickListener { addFeature() })
        binding.addImage.setOnClickListener(View.OnClickListener { addImage() })
        val list= listOf<String>("abc","def")
        val shapeAdapter:ArrayAdapter<*>
        shapeAdapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1)
        binding.kitchenShapeSpinner.adapter=shapeAdapter
        binding.kitchenShapeSpinner.onItemClickListener=this

    }

    private fun addFeature() {
        val featuresBinding=CustomviewFeaturesBinding.inflate(layoutInflater)
        featureArray.add(featuresBinding)
        featuresBinding.cancel.setOnClickListener(View.OnClickListener {
            featureArray.remove(featuresBinding)
        })
        binding.featuresLayout.addView(featuresBinding.root)
    }
    private fun addImage() {
        val imageBinding=CustomViewImageBinding.inflate(layoutInflater)
        imageArray.add(imageBinding)
        imageBinding.delete.setOnClickListener(View.OnClickListener {
            imageArray.remove(imageBinding)
        })
        binding.imageLayout.addView(imageBinding.root)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0.getItemAtPosition(p2).toString()
    }


}