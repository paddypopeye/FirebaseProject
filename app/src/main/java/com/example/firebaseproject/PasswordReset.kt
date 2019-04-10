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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class PasswordReset : AppCompatActivity() {

    private var user_email : EditText? = null
    private var reset_button : Button? = null
    private var firebasse: FirebaseAuth? = null
    private var login_btn: Button? = null
    private var reg_btn_reset: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        user_email = findViewById(R.id.reset_input)
        reset_button = findViewById(R.id.reset_btn)
        FirebaseApp.initializeApp(this)
        firebasse = FirebaseAuth.getInstance()
        login_btn = findViewById(R.id.login_btn_reset)
        reg_btn_reset = findViewById(R.id.reg_btn_reset)

        reset_button?.setOnClickListener {
            ResetPassword()
        }

        reg_btn_reset?.setOnClickListener {
            startActivity(Intent(this@PasswordReset, TestActivity::class.java))
        }

        login_btn?.setOnClickListener {
            startActivity(Intent(this@PasswordReset, LoginActivity::class.java))
        }
    }

    private fun ResetPassword(){
        val email = user_email?.text.toString().trim()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(applicationContext, "Please enter your email address", Toast.LENGTH_LONG).show()
        }
        else{
            firebasse?.sendPasswordResetEmail(email)?.addOnCompleteListener(
                object: OnCompleteListener<Void>{
                    override fun onComplete(task: Task<Void>) {
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext,"A Reset email has been sent to you", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val error = task.exception?.message
                            Toast.makeText(applicationContext, "An error occurred please try again!!", Toast.LENGTH_LONG).show()

                        }
                    }

                }
            )
        }
    }
}
