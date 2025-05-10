package com.ferhat.grademate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ferhat.grademate.databinding.ActivitySplashBinding
import com.shashank.sony.fancytoastlib.FancyToast

class ActivitySplash : AppCompatActivity() {
    private val TAG = "FERHAT"
    private lateinit var splashB: ActivitySplashBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        // binding
        splashB = ActivitySplashBinding.inflate(layoutInflater)
        // when it loaded, i couldn't see the images
        splashB.ivMjolnir.setImageResource(R.drawable.mjolnir)
        splashB.imgBtnEnter.setImageResource(R.drawable.purple_button_pressed)
        splashB.imgDropIt.setImageResource(R.drawable.black_button_n)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(splashB.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // START
        Log.i(TAG, "App started. Regular log")
        applicationContext.logi("Log of app context")
        this.logi("Log of 'this'")
        this.popmsg("popmsg of 'this'")

        // ANIMATIONS
        animateMjolnir()
        splashB.ivMjolnir.setOnClickListener { v ->
            animateMjolnir()
        }

        splashB.imgBtnEnter.setOnClickListener {
            Log.i("splashB", "onclick")
            this.logi("OnClick@this.logi")
            this.popmsg("onclick@this.popmsg")
            FancyToast.makeText(applicationContext, "imgBtnEnter", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show()
        }

        // Enter button animation:
        splashB.imgBtnEnter.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    this.logi("touchDown")
                    splashB.imgBtnEnter.setImageResource(R.drawable.purple_button_neutral)

                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    Log.i(TAG, "touchUp")
                    splashB.imgBtnEnter.setImageResource(R.drawable.purple_button_pressed)

                    // Flying animation
                    val flyanim = AnimationUtils.loadAnimation(applicationContext, R.anim.flying)
                    splashB.ivMjolnir.startAnimation(flyanim)

                    // Delay
                    object : CountDownTimer(1000, 1000){
                        override fun onTick(millisUntilFinished: Long) {
                            this.logi("tick")
                        }

                        override fun onFinish() {
                            this.logi("timer done")
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }

                    }.start()

                }
            }
            false
        }

//        splashB.frameLayout.setOnClickListener { this.logi("framelayout") }

    }

    fun animateMjolnir() {
        val animFall = AnimationUtils.loadAnimation(applicationContext, R.anim.dropping)
        val animMjolnir = AnimationUtils.loadAnimation(applicationContext, R.anim.dropping_turning)
        splashB.ivMjolnir.startAnimation(animFall)
        splashB.ivMjolnir.startAnimation(animMjolnir)
    }
}