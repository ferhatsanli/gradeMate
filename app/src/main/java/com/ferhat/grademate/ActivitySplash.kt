package com.ferhat.grademate

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ferhat.grademate.databinding.ActivitySplashBinding

class ActivitySplash : AppCompatActivity() {
    private val TAG = "FERHAT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // binding
        val splashB = ActivitySplashBinding.inflate(layoutInflater)

        splashB.imgBtnEnter.setOnClickListener {
            Log.i(TAG, "onClick")
            // open main activity
            val activityTransporter = Intent(this, MainActivity::class.java)
            startActivity(activityTransporter)
        }

        // Enter button animation:
        splashB.imgBtnEnter.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i(TAG, "touchDown")
                    splashB.imgBtnEnter.setImageResource(R.drawable.purple_button_pressed)

                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    Log.i(TAG, "touchUp")
                    splashB.imgBtnEnter.setImageResource(R.drawable.purple_button_neutral)
                }
            }
            false
        }
    }
}