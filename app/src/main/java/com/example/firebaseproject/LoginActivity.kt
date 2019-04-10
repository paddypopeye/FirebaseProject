package com.example.firebaseproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private var user_email: EditText? = null
    private var user_password: EditText? = null
    private var loginBtn:Button? = null
    private var returnBtn:Button? = null
    private var firebaseAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user_email = findViewById(R.id.user_email_login)
        user_password = findViewById(R.id.user_password_login)
        loginBtn = findViewById(R.id.button_loginActivity)
        returnBtn = findViewById(R.id.btn_goBack)
        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()


        loginBtn?.setOnClickListener{
            LoginUser()
        }

        returnBtn?.setOnClickListener{
            Reset()
        }
    }

     fun LoginUser(){
        var email_text = user_email?.text.toString().trim()
        var password_text = user_password?.text.toString().trim()

        if(TextUtils.isEmpty(email_text)){
            Toast.makeText(applicationContext, "The email field cannot be blank", Toast.LENGTH_LONG).show()
        }
        else if(TextUtils.isEmpty(password_text)){
            Toast.makeText(applicationContext, "The password field cannot be blank", Toast.LENGTH_LONG).show()
        }

        else{

            firebaseAuth?.signInWithEmailAndPassword(email_text,password_text)?.addOnCompleteListener(object: OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext, "You are now logged in", Toast.LENGTH_LONG).show()
                        val user:FirebaseUser =  firebaseAuth!!.currentUser!!
                        if(user.isEmailVerified){
                            Toast.makeText(applicationContext, "The account has been verified", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@LoginActivity, TestActivity::class.java))

                        }
                        else{
                            Toast.makeText(applicationContext,"Account not verified", Toast.LENGTH_LONG).show()
                        }

                    }

                    else {

                        val error = task.exception?.message

                        Toast.makeText(applicationContext, "Something went wrong pleasse try again..." + error, Toast.LENGTH_LONG).show()

                    }
                }
            })
        }
    }

    public fun Reset() {

        startActivity(Intent(this@LoginActivity, PasswordReset::class.java))
    }
}
