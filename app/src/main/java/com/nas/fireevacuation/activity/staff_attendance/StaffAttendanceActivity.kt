package com.nas.fireevacuation.activity.staff_attendance

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.gallery.GalleryActivity
import com.nas.fireevacuation.activity.my_profile.MyProfileActivity
import com.nas.fireevacuation.activity.staff_attendance.adapter.ViewPagerAdapter
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.PreferenceManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.security.auth.Subject

class StaffAttendanceActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var homeButton: ImageView
    lateinit var backButton: ImageView
    lateinit var myProfile: ImageView
    lateinit var gallery: ImageView
    lateinit var search: ImageView
    lateinit var className: TextView
    lateinit var date: TextView
    lateinit var subject: TextView
    lateinit var gallery: ImageView
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_attendence)
        context = this
        homeButton = findViewById(R.id.homeButton)
        backButton = findViewById(R.id.back_button)
        gallery = findViewById(R.id.gallery)
        search = findViewById(R.id.search)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        myProfile = findViewById(R.id.myProfile)
        date = findViewById(R.id.date)
        subject = findViewById(R.id.subject)
        className = findViewById(R.id.className)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = current.format(formatter)
        try {
            date.text = formatted
            className.text = PreferenceManager.getClassName(context)
            subject.text = PreferenceManager.getSubject(context)
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
            viewPagerAdapter.add(AllStudentsFragment(), "ALL")
            viewPagerAdapter.add(PresentStudentsFragment(), "PRESENT")
            viewPagerAdapter.add(AbsentStudentsFragment(), "ABSENT")
            viewPager!!.adapter = viewPagerAdapter
            tabLayout!!.setupWithViewPager(viewPager)
            tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
            homeButton.setOnClickListener {
                val intent = Intent(context, StaffHomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()
            }
            gallery.setOnClickListener {
                val intent = Intent(context, GalleryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()
            }
            search.setOnClickListener {

            }
            myProfile.setOnClickListener {
                val intent = Intent(context, MyProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()
            }
            backButton.setOnClickListener {
                val intent = Intent(context, StaffHomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()
            }
        }catch (e:Exception) {
            Log.e("Error",e.toString())
        }


    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(context, StaffHomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }
}