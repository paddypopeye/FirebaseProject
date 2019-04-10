package com.example.firebaseproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }


    public fun changeEmail_btn(view: View){
        startActivity(Intent(this@TestActivity, changeEmail::class.java))
    }
}

