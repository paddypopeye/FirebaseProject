package com.example.firebaseproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class changeEmail : AppCompatActivity() {

    var user_email : EditText? = null
    var user_password : EditText? = null
    var new_email : EditText? = null
    var change : Button? = null
    var firebaseAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        user_email = findViewById(R.id.email_change)
        user_password = findViewById(R.id.pass_change)
        new_email = findViewById(R.id.email_change_new)
        change = findViewById(R.id.changeEmail_btn)
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth?.currentUser!!

        change?.setOnClickListener {
            updateEmail()
        }
    }

    public fun updateEmail(){
        var email  = user_email?.text.toString().trim()
        var password = user_password?.text.toString().trim()
        var newEmail = new_email?.text.toString().trim()

        var userInfo = EmailAuthProvider.getCredential(email,password)
        user?.reauthenticate(userInfo)?.addOnCompleteListener(object:  OnCompleteListener<Void>{
            override fun onComplete(p0: Task<Void>) {
                user!!.updateEmail(newEmail).addOnCompleteListener(object: OnCompleteListener<Void>{

                    override fun onComplete(task: Task<Void>) {
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext, "Your Email has been changed", Toast.LENGTH_LONG).show()
                        }
                        else{
                            var error = task.exception?.message
                            Toast.makeText(applicationContext, "There was an error... " + error, Toast.LENGTH_LONG).show()

                        }
                    }

                })
            }

        })
    }
}
