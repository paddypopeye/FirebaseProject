package com.example.firebaseproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {
    private var user_email:EditText? = null
    private var user_password:EditText? = null
    private var loginBtn:Button? = null
    private var returnBtn:Button? = null
    private var firbaseAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user_email = findViewById(R.id.user_email)
        user_password = findViewById(R.id.user_password)
        loginBtn = findViewById(R.id.button_loginActivity)
        returnBtn = findViewById(R.id.btn_goBack)
        FirebaseApp.initializeApp(this)
        firbaseAuth = FirebaseAuth.getInstance()

        loginBtn?.setOnClickListener{
            LoginUser()
        }

        returnBtn?.setOnClickListener{
            startActivity(Intent(this@LoginActivity, PasswordReset::class.java))
        }



    }

    private fun LoginUser(){
        var email_text = user_email?.text.toString().trim()
        var password_text = user_password?.text.toString().trim()

        if(TextUtils.isEmpty(email_text)){
            Toast.makeText(applicationContext, "The email field cannot be blank", Toast.LENGTH_LONG).show()
        }
        else if(TextUtils.isEmpty(password_text)){
            Toast.makeText(applicationContext, "The password field cannot be blank", Toast.LENGTH_LONG).show()
        }

        else{

            firbaseAuth?.signInWithEmailAndPassword(email_text,password_text)?.addOnCompleteListener(object: OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext, "You are now logged in", Toast.LENGTH_LONG).show()

                    }

                    else {
                        val error = task.exception?.message

                        Toast.makeText(applicationContext, "Something went wrong pleasse try again..." + error, Toast.LENGTH_LONG).show()

                    }
                }
            })
        }
    }


}
