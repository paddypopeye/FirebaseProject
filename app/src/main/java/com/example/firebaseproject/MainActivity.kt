package com.example.firebaseproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var signup_btn: Button? = null
    private var user_email_editText: EditText? = null
    private var user_password_editText: EditText? = null
    private var firebaseAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signup_btn = findViewById<Button>(R.id.btn_sign_up)
        user_email_editText = findViewById<EditText>(R.id.user_email)
        user_password_editText = findViewById<EditText>(R.id.user_password)
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance()


        signup_btn?.setOnClickListener {
            View.OnClickListener{
                   RegisterNewUser()
            }
        }
    }


    private fun RegisterNewUser(){
        var email_text = user_email_editText?.text.toString().trim()
        var password = user_password_editText?.text.toString().trim()



        if(TextUtils.isEmpty(email_text)){
            Toast.makeText(applicationContext, "The Email field cannot be empty", Toast.LENGTH_SHORT).show()

        }
 
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(applicationContext, "The Password field cannot be empty", Toast.LENGTH_SHORT).show()

        }
        else{

            firebaseAuth?.createUserWithEmailAndPassword(email_text,password)?.addOnCompleteListener(
                object:OnCompleteListener<AuthResult>{
                    override fun onComplete(task: Task<AuthResult>) {

                        if(task.isSuccessful){
                            Toast.makeText(applicationContext, "Account Created", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            val error = task.exception?.message
                            Toast.makeText(applicationContext, "An error occured please try again!!", Toast.LENGTH_SHORT).show()
                        }

                    }

                })
        }
    }
}

