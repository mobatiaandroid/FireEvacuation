package com.nas.fireevacuation.activity.staff_home

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.Lists
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.YearGroups
import com.nas.fireevacuation.activity.staff_attendance.StaffAttendanceActivity
import com.nas.fireevacuation.activity.staff_home.model.StudentModel
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StaffHomeActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var attendenceButton: ImageView
    lateinit var backButton: ImageView
    lateinit var  extras: Bundle
    lateinit var classID: String
    lateinit var staffName: TextView
    lateinit var imageA: ImageView
    lateinit var imageB: ImageView
    lateinit var imageC: ImageView
    lateinit var count: TextView
    lateinit var totalStudents: TextView
    var progressBarDialog: ProgressBarDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_home_new)
        context = this
        progressBarDialog = ProgressBarDialog(context)
        attendenceButton = findViewById(R.id.attendence)
        backButton = findViewById(R.id.back_button)
        staffName = findViewById(R.id.staffName)
        imageA = findViewById(R.id.imageA)
        imageB = findViewById(R.id.imageB)
        imageC = findViewById(R.id.imageC)
        count = findViewById(R.id.count)
        totalStudents = findViewById(R.id.totalStudents)
        staffName.text = PreferenceManager.getStaffName(context)
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
        extras = intent.extras!!
        classID = extras.getString("classID").toString()
        Log.e("Class ID:",classID)
        studentListCall(classID)
    }

    private fun studentListCall(classID: String) {
        var studentsResponse: StudentModel
        var studentsArrayList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.Lists> = ArrayList()
        var i: Int = 0
        val call: Call<StudentModel> = ApiClient.getClient.studentsAPICall(
            PreferenceManager.getAccessToken(context),
            classID
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<StudentModel> {
            override fun onResponse(call: Call<StudentModel>, response: Response<StudentModel>) {
                progressBarDialog!!.hide()
                if (!response.body()!!.equals("")) {
                    studentsResponse = response.body()!!
                    Log.e("Response",response.body().toString())
                    if (studentsResponse.responsecode.equals("100")) {
                        if (studentsResponse.message.equals("success")) {
                            Log.e("Response",studentsResponse.data.toString())
                            while (i<studentsResponse.data.lists.size) {
                                studentsArrayList.add(studentsResponse.data.lists[i])
                                i++
                            }
                            Log.e("list",studentsResponse.data.lists.toString())
                            Glide.with(context)
                                .load(studentsArrayList[0].photo)
                                .into(imageA)
                            Glide.with(context)
                                .load(studentsArrayList[1].photo)
                                .into(imageB)
                            Glide.with(context)
                                .load(studentsArrayList[2].photo)
                                .into(imageC)
                            count.text = ((studentsArrayList.size)-3).toString()+"+"
                            totalStudents.text = studentsArrayList.size.toString()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<StudentModel>, t: Throwable) {
                progressBarDialog!!.hide()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(context, WelcomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }
}