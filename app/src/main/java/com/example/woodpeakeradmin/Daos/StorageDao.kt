package com.example.woodpeakeradmin.Daos
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

object StorageDao {
    val productImageRef = FirebaseStorage.getInstance().getReference("productImages")
    fun uploadProductImage(imagePathUri: Uri?, fileName: String?): UploadTask? {
        Log.d("TAG", "storageDao uploadImage start")
        return productImageRef.child(fileName!!).putFile(imagePathUri!!)
    }
    fun deleteProductImage(fileName: String?): Task<Void> {
        return productImageRef.child(fileName!!).delete()
    }
    fun getImageUrlOfProduct(filename: String?): Task<Uri?>? {
        return productImageRef.child(filename!!).downloadUrl
    }

}