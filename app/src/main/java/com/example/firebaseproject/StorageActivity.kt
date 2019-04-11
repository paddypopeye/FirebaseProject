package com.example.firebaseproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*

class StorageActivity : AppCompatActivity() {

    var upload_btn : Button? = null
    var image: ImageView? = null
    var imageUri: Uri? = null
    private var storage : FirebaseStorage? = null
    private var imageRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)


        upload_btn = findViewById(R.id.btn_storage)
        image = findViewById(R.id.storageImg)
        storage = FirebaseStorage.getInstance()
        imageRef = storage?.reference?.child("Images")

        upload_btn?.setOnClickListener {
            AddTheImageToFirebase()
        }
        image?.setOnClickListener {
            PickImageFromGallery()
        }
    }

    private fun AddTheImageToFirebase(){
            if(imageUri != null){
                val reference  = imageRef?.child(UUID.randomUUID().toString())
                reference?.putFile(imageUri!!)?.addOnCompleteListener(object: OnCompleteListener<UploadTask.TaskSnapshot>{
                    override fun onComplete(task: Task<UploadTask.TaskSnapshot>) {
                            if(task.isSuccessful){
                                Toast.makeText(applicationContext,"Image uploaded!!", Toast.LENGTH_LONG).show()
                            }
                            else{
                                val error = task.exception?.message
                                Toast.makeText(applicationContext, "Error uploading the image", Toast.LENGTH_LONG).show()
                            }
                    }

                })
            }
    }

    private fun PickImageFromGallery(){
        val gallery = Intent()
        gallery.type = "Image/*"
        gallery.action  = Intent.ACTION_GET_CONTENT
        startActivityForResult(gallery, 111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null){
                    imageUri = data.data
                    image?.setImageURI(imageUri)
                try {
                    val imgBitMap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    image?.setImageBitmap(imgBitMap)
                }
                catch(error: IOException){

                    Toast.makeText(applicationContext, "Error  " + error, Toast.LENGTH_LONG).show()
                }
        }
    }
}
