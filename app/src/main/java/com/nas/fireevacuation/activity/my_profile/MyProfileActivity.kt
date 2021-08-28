package com.nas.fireevacuation.activity.my_profile

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.sign_in.SignInActivity
import com.nas.fireevacuation.activity.staff_attendance.StaffAttendanceActivity
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity

class MyProfileActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var backButton: ImageView
    lateinit var homeButton: ImageView
    lateinit var attendanceButton: ImageView
    lateinit var changePassword: TextView
    lateinit var logout: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        context = this
        backButton = findViewById(R.id.back_button)
        homeButton = findViewById(R.id.home)
        attendanceButton = findViewById(R.id.attendence)
        changePassword = findViewById(R.id.changePassword)
        logout = findViewById(R.id.logout)
        backButton.setOnClickListener {
            val intent = Intent(context, StaffAttendanceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        homeButton.setOnClickListener {
            val intent = Intent(context, StaffHomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        attendanceButton.setOnClickListener {
            val intent = Intent(context, StaffAttendanceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        logout.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
    }
}