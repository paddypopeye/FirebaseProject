package com.example.firebaseproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ProfileActivity : AppCompatActivity() {
    var first_name : EditText? = null
    var last_name : EditText? = null
    var user_name : EditText? = null
    var submit : Button? = null
    var firebaseAuth : FirebaseAuth? = null
    var firebaseDatabase: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        first_name = findViewById(R.id.first_name)
        last_name = findViewById(R.id.last_name)
        user_name = findViewById(R.id.user_name)
        submit = findViewById(R.id.submit_profile)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth!!.currentUser!!.uid)


        submit?.setOnClickListener {
            SaveProfileInfo()
        }
    }

    fun SaveProfileInfo(){
            val firstName = first_name?.text.toString().trim()
            val lastName = last_name?.text.toString().trim()
            val userName = user_name?.text.toString().trim()

            if(TextUtils.isEmpty(firstName)){

                    Toast.makeText(applicationContext, "The first name cannot be empty", Toast.LENGTH_LONG).show()
            }
            else if(TextUtils.isEmpty(lastName)){

            Toast.makeText(applicationContext, "The last name cannot be empty", Toast.LENGTH_LONG).show()
            }

            else{

                val userinfo = HashMap<String,Any>()
                userinfo.put("firstName", firstName)
                userinfo.put("lastName", lastName)
                userinfo.put("userName", userName)

                firebaseDatabase?.updateChildren(userinfo)?.addOnCompleteListener(object: OnCompleteListener<Void>{
                    override fun onComplete(task: Task<Void>) {
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext, "Your profile was updated", Toast.LENGTH_LONG).show()

                        }
                        else{

                            val error = task.exception?.message

                            Toast.makeText(applicationContext, "An error occured updating the profile" + error , Toast.LENGTH_LONG).show()

                        }
                    }

                })
            }

    }

}
