package com.example.shoppingapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Initialize views
        val splashLogo = findViewById<ImageView>(R.id.splash_logo)
        val loadingProgress = findViewById<ProgressBar>(R.id.loading_progress)

        // Start animation
        splashLogo.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(800)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                loadingProgress.visibility = View.VISIBLE

                // Optional: Add transition to main activity after animation
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 1000) // Match this delay with animation duration
            }.start()
    }
}