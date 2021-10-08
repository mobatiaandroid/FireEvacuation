package com.nas.fireevacuation.activity.staff_home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.EvacuationActivity
import com.nas.fireevacuation.activity.gallery.GalleryActivity
import com.nas.fireevacuation.activity.my_profile.MyProfileActivity
import com.nas.fireevacuation.activity.session_select.SessionSelectActivity
import com.nas.fireevacuation.activity.staff_attendance.StaffAttendanceActivity
import com.nas.fireevacuation.activity.staff_home.model.assembly_points_model.AssemblyPointsModel
import com.nas.fireevacuation.activity.staff_home.model.assembly_points_model.Lists
import com.nas.fireevacuation.activity.staff_home.model.students_model.StudentModel
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import com.ncorti.slidetoact.SlideToActView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class StaffHomeActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var attendenceButton: ImageView
    lateinit var myProfile: ImageView
    lateinit var gallery: ImageView
//    lateinit var backButton: ImageView
    lateinit var  extras: Bundle
    lateinit var classID: String
    lateinit var staffName: TextView
    lateinit var imageA: ImageView
    lateinit var imageB: ImageView
    lateinit var imageC: ImageView
    lateinit var count: TextView
    lateinit var greeting: TextView
    lateinit var totalStudents: TextView
    lateinit var presentStudents: TextView
    lateinit var absentStudents: TextView
    lateinit var progressBarPresent: ProgressBar
    lateinit var progressBarAbsent: ProgressBar
    lateinit var className: TextView
    lateinit var date: TextView
    lateinit var assemblyAreaSelector: View
    lateinit var area: TextView
    lateinit var slider: SlideToActView
    lateinit var evacuateButton: View
    lateinit var presentStudentList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.students_model.Lists>
    lateinit var absentStudentList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.students_model.Lists>
    var progressBarDialog: ProgressBarDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_home_new)
        context = this
        progressBarDialog = ProgressBarDialog(context)
        attendenceButton = findViewById(R.id.attendence)
        gallery = findViewById(R.id.gallery)
        myProfile = findViewById(R.id.myProfile)
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
        greeting = findViewById(R.id.greeting)
        date = findViewById(R.id.date)
        assemblyAreaSelector = findViewById(R.id.assemblyAreaSelector)
        area = findViewById(R.id.area)
        slider = findViewById(R.id.slider)
        evacuateButton = findViewById(R.id.evacuateButton)
        var slideCompleteListener: OnSlideCompleteListener
        presentStudentList = ArrayList()
        absentStudentList = ArrayList()

        gallery.setOnClickListener {
            val intent = Intent(context, GalleryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        myProfile.setOnClickListener {
            val intent = Intent(context, MyProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        slider.onSlideCompleteListener = object : OnSlideCompleteListener,
            SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                val intent = Intent(context, SessionSelectActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()
            }
        }
        evacuateButton.setOnClickListener {
            if (area.text.equals("Assembly Area")) {
                CommonMethods.showLoginErrorPopUp(context,"Alert","Please Select Assembly Point")
            } else {
                val intent = Intent(context, EvacuationActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()
            }
        }
        assemblyAreaSelector.setOnClickListener {
            var assemblyPointsResponse: AssemblyPointsModel
            var assemblyPointsList: ArrayList<Lists> = ArrayList()
            var i: Int = 0
            val call: Call<AssemblyPointsModel> = ApiClient.getClient.assemblyPoints(
                PreferenceManager.getAccessToken(context)
            )
            call.enqueue(object : Callback<AssemblyPointsModel> {
                override fun onResponse(
                    call: Call<AssemblyPointsModel>,
                    response: Response<AssemblyPointsModel>
                ) {
//                    Log.e("Assembly Response",response.body().toString())
                    if (!response.body()!!.equals("")) {
                        assemblyPointsResponse = response.body()!!
                        if (assemblyPointsResponse.responsecode.equals("100")) {
                            if (assemblyPointsResponse.message.equals("success")) {
                                while (i < assemblyPointsResponse.data.lists.size) {
                                    assemblyPointsList.add(assemblyPointsResponse.data.lists[i])
                                    i++
                                }
//                                Log.e("Assembly Points2", assemblyPointsList.toString())
                                var i = 0
                                var assemblyPointsStringList: ArrayList<String> = ArrayList()
                                while (i<assemblyPointsList.size){
                                    assemblyPointsStringList.add(assemblyPointsList[i].assembly_point)
                                    i++
                                }
//                                Log.e("Assembly Points3", assemblyPointsStringList.toString())
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Select Session")
                                var checkedItem = -1
                                builder.setSingleChoiceItems(assemblyPointsStringList.toTypedArray(), checkedItem) { dialog, which ->
                                    checkedItem = which
                                }
                                builder.setPositiveButton("OK") { dialog, which ->
                                    area.text = assemblyPointsStringList[checkedItem]
                                    PreferenceManager.setAssemblyPoint(context, assemblyPointsList[checkedItem].id)
                                }
                                builder.setNegativeButton("Cancel", null)
                                val dialog = builder.create()
                                dialog.show()

                            }
                        } else if (assemblyPointsResponse.responsecode.equals("402")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Session Expired")
                            CommonMethods.getAccessTokenAPICall(context)
                        }
                    }
                }

                override fun onFailure(call: Call<AssemblyPointsModel>, t: Throwable) {
                    CommonMethods.showLoginErrorPopUp(context,"Alert","Some Error Occured")
                }

            })

        }
        attendenceButton.setOnClickListener{
            val intent = Intent(context, StaffAttendanceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }




        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val currentDate = current.format(formatter)
        greetingSetter()
        date.text = currentDate
        classID = PreferenceManager.getClassID(context)
        className.text = PreferenceManager.getClassName(context)
        staffName.text = PreferenceManager.getStaffName(context)
        Log.e("Class ID:",classID)
        studentListCall(classID)
    }

    private fun greetingSetter() {
        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        if (hour in 6..12) {
            greeting.text = "Good Morning"
        } else if (hour == 12) {
            greeting.text = "Noon"
        } else if (hour in 13..17) {
            greeting.text = "Good Afternoon"
        } else if (hour in 17..22) {
            greeting.text = "Good Evening"
        } else {
            greeting.text = "Good Night"
        }
    }

    private fun showSelector(assemblyPointsList: ArrayList<Lists>) {
        var i = 0
        var assemblyPointsStringList: ArrayList<String> = ArrayList()
        while (i<assemblyPointsList.size){
            assemblyPointsStringList.add(assemblyPointsList[i].assembly_point)
        }
        Log.e("Assembly Points3", assemblyPointsStringList.toString())
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Session")
        var checkedItem = -1
        builder.setSingleChoiceItems(assemblyPointsStringList.toTypedArray(), checkedItem) { dialog, which ->
            checkedItem = which
        }
        builder.setPositiveButton("OK") { dialog, which ->
            area.text = assemblyPointsStringList[checkedItem]
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }



    private fun studentListCall(classID: String) {
        var studentsResponse: StudentModel
        var studentsArrayList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.students_model.Lists> = ArrayList()
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
                            PreferenceManager.setStudentList(context,studentsArrayList)
                            while (i<studentsArrayList.size){
                                if (studentsArrayList[i].present.equals("1")) {
                                    presentStudentList.add(studentsArrayList[i])
                                } else {
                                    absentStudentList.add(studentsArrayList[i])

                                }
                                i++
                            }
//                            if (presentStudentList.size > 0){
//                                PreferenceManager.setPresentList(context,presentStudentList)
//                            } else {
//                                presentStudentList = ArrayList()
//                                PreferenceManager.setPresentList(context,presentStudentList)
//                            }
//                            if (absentStudentList.size > 0){
//                                PreferenceManager.setAbsentList(context, absentStudentList)
//                            } else {
//                                absentStudentList = ArrayList()
//                                PreferenceManager.setAbsentList(context,absentStudentList)
//                            }
                            progressBarPresent.progressDrawable.setColorFilter(
                                context.resources.getColor(R.color.green), android.graphics.PorterDuff.Mode.SRC_IN)
                            progressBarAbsent.progressDrawable.setColorFilter(
                                context.resources.getColor(R.color.pink), android.graphics.PorterDuff.Mode.SRC_IN)

                            presentStudents.text = presentStudentList.size.toString()
                            absentStudents.text = absentStudentList.size.toString()
                            progressBarPresent.progress = ((presentStudentList.size.toFloat()/studentsArrayList.size.toFloat())*100).toInt()
                            Log.e("progress",((progressBarPresent.progress).toString()))
                            progressBarAbsent.progress = ((absentStudentList.size.toFloat()/studentsArrayList.size.toFloat())*100).toInt()
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
                            Log.e("CLass ID",PreferenceManager.getClassID(context))
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

    }
}
interface OnSlideCompleteListener {

    fun onSlideComplete(view: SlideToActView)
}

