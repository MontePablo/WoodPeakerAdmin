package com.example.woodpeakeradmin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.net.toUri
import com.example.woodpeakeradmin.Daos.StorageDao
import com.example.woodpeakeradmin.databinding.*
import com.example.woodpeakeradmin.models.Product
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.concurrent.timerTask

class NewAd : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding:ActivityNewAdBinding
    var featureArray=ArrayList<CustomviewFeaturesBinding>()
    var imageViewTable:Hashtable<Int,CustomviewImageBinding> = Hashtable<Int,CustomviewImageBinding>()
    var productShape=""
    var addonArray=ArrayList<CustomviewAddonBinding>()
    lateinit var currentImageLayout:LinearLayout

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewAdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentImageLayout=binding.imageLayoutRed
        binding.addFeature.setOnClickListener(View.OnClickListener { addFeature() })
        binding.addImage.setOnClickListener(View.OnClickListener { addImage(currentImageLayout) })
        binding.addAddon.setOnClickListener(View.OnClickListener { addAddon() })
        val list= listOf<String>("Island shape kitchen","I shape kitchen","U shape kitchen","L shape kitchen","others")
        val shapeAdapter:ArrayAdapter<*>
        shapeAdapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list)
        binding.kitchenShapeSpinner.adapter=shapeAdapter
        binding.kitchenShapeSpinner.onItemSelectedListener=this

        binding.colBlack.setOnClickListener(View.OnClickListener { colorBtnPress("Black")})
        binding.colBlue.setOnClickListener(View.OnClickListener { colorBtnPress("Blue")})
        binding.colRed.setOnClickListener(View.OnClickListener { colorBtnPress("Red")})
        binding.colWhite.setOnClickListener(View.OnClickListener { colorBtnPress("White")})
        binding.colGreen.setOnClickListener(View.OnClickListener { colorBtnPress("Green")})
        binding.colYellow.setOnClickListener(View.OnClickListener { colorBtnPress("Yellow")})


    }
    fun uploadData(){
        val product=Product()
        product.title=binding.productName.text.toString()
        product.price=binding.productPrice.text.toString()
        product.shape=productShape
        for(f in featureArray){
            product.features.add(f.features.text.toString())
        }
        product.description=binding.productDescription.text.toString()
        var bluePics=ArrayList<String>()
        var redPics=ArrayList<String>()
        var blackPics=ArrayList<String>()
        var yellowPics=ArrayList<String>()
        var greenPics=ArrayList<String>()
        var whitePics=ArrayList<String>()
        for(f in imageViewTable) {
            if (f.storeColorId.text.toString() == binding.colBlue.id.toString()) {
                bluePics.add(f.storeLink.text.toString())
            } else if (f.storeColorId.text.toString() == binding.colRed.id.toString()) {
                redPics.add(f.storeLink.text.toString())
            } else if (f.storeColorId.text.toString() == binding.colYellow.id.toString()) {
                yellowPics.add(f.storeLink.text.toString())
            } else if (f.storeColorId.text.toString() == binding.colBlack.id.toString()) {
                blackPics.add(f.storeLink.text.toString())
            } else if (f.storeColorId.text.toString() == binding.colWhite.id.toString()) {
                whitePics.add(f.storeLink.text.toString())
            } else if (f.storeColorId.text.toString() == binding.colGreen.id.toString()) {
                greenPics.add(f.storeLink.text.toString())
            }else{

            }
        }
        if(bluePics.isNotEmpty()) product.images.put("Blue",bluePics as Objects)
        if(greenPics.isNotEmpty()) product.images.put("Green",greenPics as Objects)
        if(whitePics.isNotEmpty()) product.images.put("White",whitePics as Objects)
        if(blackPics.isNotEmpty()) product.images.put("Black",blackPics as Objects)
        if(redPics.isNotEmpty()) product.images.put("Red",redPics as Objects)
        if(yellowPics.isNotEmpty()) product.images.put("Yellow",yellowPics as Objects)
        for(f in addonArray){
            val hm=HashMap<String,Objects>()
            hm.put("Name",f.addonName.text.toString() as Objects)
            hm.put("Price",f.addonPrice.text.toString() as Objects)
            hm.put("Image",f.storeLink.text.toString() as Objects)
            product.addons.add(hm)
        }

    }
    fun colorBtnPress(col:String){
        when(col){
            "Red" ->{
                currentImageLayout=binding.imageLayoutRed
                hideAllLayoutsExcept(currentImageLayout)
            }
            "Green" ->{
                currentImageLayout=binding.imageLayoutGreen
                hideAllLayoutsExcept(currentImageLayout)
            }
            "Blue" ->{
               currentImageLayout=binding.imageLayoutBlue
                hideAllLayoutsExcept(currentImageLayout)
            }
            "White" ->{
                currentImageLayout=binding.imageLayoutWhite
                hideAllLayoutsExcept(currentImageLayout)

            }
            "Black" ->{
                currentImageLayout=binding.imageLayoutBlack
                hideAllLayoutsExcept(currentImageLayout)
            }
            "Yellow" ->{
                currentImageLayout=binding.imageLayoutYellow
                hideAllLayoutsExcept(currentImageLayout)
            }

        }

    }
    fun hideAllLayoutsExcept(exception:LinearLayout){
        binding.imageLayoutRed.visibility=View.GONE
        binding.imageLayoutGreen.visibility=View.GONE
        binding.imageLayoutBlue.visibility=View.GONE
        binding.imageLayoutWhite.visibility=View.GONE
        binding.imageLayoutYellow.visibility=View.GONE
        binding.imageLayoutBlack.visibility=View.GONE
        exception.visibility=View.VISIBLE

    }

    fun addAddon(){
        val addonBinding = CustomviewAddonBinding.inflate(layoutInflater)
        binding.addonLayout.addView(addonBinding.root)
        addonArray.add(addonBinding)
        addonBinding.addImage.setOnClickListener(View.OnClickListener {
            addImage(addonBinding.imageLayoutInAddon,"")
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
    private fun progressBarFunc(viewBinding: CustomviewImageBinding){
        var counter=0
        var timer=Timer()
        var timertask= timerTask {
            run(){
                super.runOnUiThread(Runnable {
                    viewBinding.progressBar.visibility=View.VISIBLE
                    counter++;
                    viewBinding.progressBar.setProgress(counter)
                    if(counter==100){
                        timer.cancel()
                        viewBinding.progressBar.visibility=View.INVISIBLE
                    }
                })
            }
        }
        timer.schedule(timertask,0,15)
    }
    private fun addImage(imageLayout: LinearLayout,flag:String) {
        val imageBinding=CustomviewImageBinding.inflate(layoutInflater)
        imageBinding.delete.setOnClickListener(View.OnClickListener {
            imageViewTable.remove(imageBinding.hashCode())
            imageLayout.removeView(imageBinding.root)
        })
        imageBinding.retry.setOnClickListener(View.OnClickListener {
            val imageUri = Uri.parse(imageBinding.storeUri.text.toString())
            CoroutineScope(Default).launch { uploadImage(imageBinding, imageUri) }
            imageBinding.retry.visibility = View.INVISIBLE
            binding.retryToast.visibility = View.INVISIBLE
            imageBinding.imageview.setImageURI(imageUri) })

        imageBinding.insert.setOnClickListener(View.OnClickListener {
            if(flag=="fature"){hhhhh7467

            }
            photoPick(imageBinding.hashCode())
            imageViewTable.put(imageBinding.hashCode(),imageBinding)
        })
        imageLayout.addView(imageBinding.root)
            imageBinding.storeColorId.setText(imageLayout.id.toString())
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
        else{
            var imageUri=data!!.data
            Log.d("TAG","onActivityResult Image received")
            val imageBinding= imageViewTable[requestCode]
            imageBinding?.imageview?.setImageURI(imageUri)
            imageBinding?.storeUri?.text = imageUri.toString()
            imageBinding?.insert?.visibility=View.GONE
            CoroutineScope(Default).launch {
                uploadImage(imageBinding!!,imageUri!!)
            }
            return
        }

    }
    suspend fun uploadImage(viewBinding:CustomviewImageBinding,imageUri: Uri){
        progressBarFunc(viewBinding)
        val directory= Environment.getExternalStorageDirectory().absolutePath
        val imageFile= File("${directory}${imageUri.path?.replace("/document/primary:","/",true)}")
        val compressedImage = Compressor.compress(this, imageFile){
            default(720,1280, Bitmap.CompressFormat.JPEG,60)
        }
        Log.d("TAG","orginalSize: ${imageFile.length()} compressedSize:${compressedImage.length()}")
        val fileName = imageUri.hashCode().toString()
        StorageDao.uploadProductImage(compressedImage.toUri(), fileName)!!.addOnSuccessListener {
            Log.d("TAG","upload success")
            StorageDao.getImageUrlOfProduct(fileName)!!.addOnSuccessListener {
                val imageLink=it.toString()
                viewBinding.storeLink.setText(imageLink)
                viewBinding.storeName.setText(fileName)
                Log.d("TAG","getting Url success ${imageLink}")
            }
        }.addOnFailureListener {
            Log.d("TAG","uploadImage onFailure: ${it.localizedMessage}")
            viewBinding.retry.visibility=View.VISIBLE
            binding.retryToast.visibility=View.VISIBLE
//            viewBinding.imagesViewImage.setImageResource(R.drawable.grey)
        }
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        productShape=p0?.selectedItem.toString()

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}