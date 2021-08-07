package com.d3if0028.katalogmov.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.fragment.HomeActivity

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar!!.hide()
        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        },3000)
    }
}