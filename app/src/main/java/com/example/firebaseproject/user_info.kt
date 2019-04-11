package com.example.firebaseproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_change_email.*

class user_info : AppCompatActivity() {

    var firstName : TextView? = null
    var lastName : TextView? = null
    var userName : TextView? = null
    var firebaseAuth : FirebaseAuth? = null
    var firebaseDatabase : DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)


        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        userName = findViewById(R.id.userName)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)


        firebaseDatabase?.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(task: DataSnapshot) {

                if(task.exists()){
                val firstname = task.child("firstName").value as String
                val lastname = task.child("lastName").value as String
                val username = task.child("userName").value as String
                    firstName?.text = firstname
                    lastName?.text = lastname
                    userName?.text = username
                }
            }
        })
    }

    public fun update(view: View){
            startActivity(Intent(this@user_info, ProfileActivity::class.java))
    }

    public fun changeEmail(view:View){
        startActivity(Intent(this@user_info, changeEmail::class.java))
    }
}