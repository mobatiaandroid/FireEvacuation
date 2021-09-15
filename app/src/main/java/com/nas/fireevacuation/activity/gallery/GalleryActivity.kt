package com.nas.fireevacuation.activity.gallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_attendance.StaffAttendanceActivity
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity

class GalleryActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var homeButton: ImageView
    lateinit var attendanceButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        context = this
        homeButton = findViewById(R.id.home)
        attendanceButton = findViewById(R.id.attendance)
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
    }
}