package com.d3if0028.katalogmov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar!!.hide()
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },3000)
    }
}