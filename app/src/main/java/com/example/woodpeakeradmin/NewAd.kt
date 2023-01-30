package com.example.woodpeakeradmin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.woodpeakeradmin.databinding.*

class NewAd : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding:ActivityNewAdBinding
    var featureArray=ArrayList<CustomviewFeaturesBinding>()
    var imageArray=ArrayList<CustomviewImageBinding>()
    var productShape=""
    var addonArray=ArrayList<CustomviewAddonBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addFeature.setOnClickListener(View.OnClickListener { addFeature() })
        binding.addImage.setOnClickListener(View.OnClickListener { addImage() })
        binding.addAddon.setOnClickListener(View.OnClickListener { addAddon() })
        val list= listOf<String>("Island shape kitchen","I shape kitchen","U shape kitchen","L shape kitchen")
        val shapeAdapter:ArrayAdapter<*>
        shapeAdapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list)
        binding.kitchenShapeSpinner.adapter=shapeAdapter
        binding.kitchenShapeSpinner.onItemSelectedListener=this


    }

    fun addAddon(){
        val addonBinding = CustomviewAddonBinding.inflate(layoutInflater)
        binding.addonLayout.addView(addonBinding.root)
        addonArray.add(addonBinding)
        addonBinding.insertimage.setOnClickListener(View.OnClickListener {
            photoPick(2)
        })
        addonBinding.cancel.setOnClickListener(View.OnClickListener {
            addonArray.remove(addonBinding)
            binding.addonLayout.removeView(addonBinding.root)
        })
    }
    private fun addFeature() {
        val featuresBinding=CustomviewFeaturesBinding.inflate(layoutInflater)
        featureArray.add(featuresBinding)
        featuresBinding.cancel.setOnClickListener(View.OnClickListener {
            featureArray.remove(featuresBinding)
            binding.featuresLayout.removeView(featuresBinding.root)
        })
        binding.featuresLayout.addView(featuresBinding.root)
    }
    private fun addImage() {
        val imageBinding=CustomviewImageBinding.inflate(layoutInflater)
        imageBinding.delete.setOnClickListener(View.OnClickListener {
            imageArray.remove(imageBinding)
            binding.imageLayout.removeView(imageBinding.root)
        })
        imageBinding.insert.setOnClickListener(View.OnClickListener {
            photoPick(1)
            imageArray.add(imageBinding)
        })
        binding.imageLayout.addView(imageBinding.root)
    }
    fun photoPick(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            Log.d("TAG","onActivityResult: Failed")
            return
        }
        else if (requestCode==1){
            var imageUri=data!!.data
            Log.d("TAG","onActivityResult Image received")
            val imageBinding= imageArray[imageArray.size-1]
            imageBinding.imageview.setImageURI(imageUri)
            imageBinding.storeUri.text = imageUri.toString()
            return
        }
        else if(requestCode==2){
            var imageUri=data!!.data
            Log.d("TAG","onActivityResult Image received")
            val imageBinding=addonArray.get(addonArray.size-1)
            imageBinding.imageview.setImageURI(imageUri)
            imageBinding.storeUri.text = imageUri.toString()
            return
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        productShape=p0?.selectedItem.toString()
        Log.d("TAG",productShape)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}