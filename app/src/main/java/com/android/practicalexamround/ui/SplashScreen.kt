package com.android.practicalexamround.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.practicalexamround.R
import com.android.practicalexamround.ui.homescreen.HomeActivity
import com.android.practicalexamround.utils.goTo

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler(Looper.getMainLooper()).postDelayed({
            goTo(HomeActivity::class.java)
            finish()
        }, 5000)

    }
}