package com.example.favdish.view.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.favdish.R
import com.example.favdish.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    lateinit var splashScreenBinding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashScreenBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)

        splashScreenBinding.tvSplashTitle.text = "Fav Dish"

        hideStatusBar()
        setSplashAnim()

    }


    private fun hideStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    private fun setSplashAnim(){
        val splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        splashScreenBinding.tvSplashTitle.animation = splashAnim

        splashAnim.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                },500)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })


    }
}