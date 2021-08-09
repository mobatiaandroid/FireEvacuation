package com.nas.fireevacuation.activity.staff_home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_attendance.StaffAttendanceActivity
import com.nas.fireevacuation.activity.welcome.WelcomeActivity

class StaffHomeActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var attendenceButton: ImageView
    lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_home_new)
        context = this
        attendenceButton = findViewById(R.id.attendence)
        backButton = findViewById(R.id.back_button)
        attendenceButton.setOnClickListener{
            val intent = Intent(context, StaffAttendanceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        backButton.setOnClickListener{
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(context, WelcomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }
}