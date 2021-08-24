package com.nas.fireevacuation.activity.staff_home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.Lists
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.YearGroups
import com.nas.fireevacuation.activity.staff_attendance.AbsentStudentsFragment
import com.nas.fireevacuation.activity.staff_attendance.StaffAttendanceActivity
import com.nas.fireevacuation.activity.staff_home.model.StudentModel
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

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
    lateinit var presentStudents: TextView
    lateinit var absentStudents: TextView
    lateinit var progressBarPresent: ProgressBar
    lateinit var progressBarAbsent: ProgressBar
    lateinit var className: TextView
    lateinit var date: TextView
    lateinit var assemblyAreaSelector: View
    lateinit var area: TextView
    lateinit var presentStudentList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.Lists>
    lateinit var absentStudentList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.Lists>
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
        presentStudents = findViewById(R.id.presentStudents)
        absentStudents = findViewById(R.id.absentStudents)
        progressBarPresent = findViewById(R.id.progressBarPresent)
        progressBarAbsent = findViewById(R.id.progressBarAbsent)
        className = findViewById(R.id.className)
        date = findViewById(R.id.date)
        assemblyAreaSelector = findViewById(R.id.assemblyAreaSelector)
        area = findViewById(R.id.area)
        presentStudentList = ArrayList()
        absentStudentList = ArrayList()

        assemblyAreaSelector.setOnClickListener {
/*
            var yearGroupsSelector: Array<String> = yearGroupsList.toTypedArray()
*/
            val call: Call<ResponseBody> = ApiClient.getClient.assemblyPoints(
                PreferenceManager.getAccessToken(context)
            )
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            /*val builder = AlertDialog.Builder(context)
            builder.setTitle("Select Session")
            var checkedItem = -1
            builder.setSingleChoiceItems(*//*yearGroupsSelector*//*, checkedItem) { dialog, which ->
                checkedItem = which
            }
            builder.setPositiveButton("OK") { dialog, which ->
                area.text = *//*yearGroupsSelector*//*[checkedItem]
            }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()*/
        }
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

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = current.format(formatter)
        date.text = formatted
        classID = extras.getString("classID").toString()
        className.text = extras.getString("className").toString()
        staffName.text = PreferenceManager.getStaffName(context)
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
                            i = 0
                            while (i<studentsArrayList.size){
                                if (studentsArrayList[i].present.equals("1")) {
                                    presentStudentList.add(studentsArrayList[i])
                                } else {
                                    absentStudentList.add(studentsArrayList[i])
                                }
                                i++
                            }
                            progressBarPresent.progressDrawable.setColorFilter(
                                context.resources.getColor(R.color.green), android.graphics.PorterDuff.Mode.SRC_IN)
                            progressBarAbsent.progressDrawable.setColorFilter(
                                context.resources.getColor(R.color.pink), android.graphics.PorterDuff.Mode.SRC_IN)

                            presentStudents.text = presentStudentList.size.toString()
                            absentStudents.text = absentStudentList.size.toString()
                            progressBarPresent.progress = (presentStudentList.size/studentsArrayList.size)*100
                            Log.e("progress",((progressBarPresent.progress).toString()))
                            progressBarAbsent.progress = (absentStudentList.size/studentsArrayList.size)*100
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