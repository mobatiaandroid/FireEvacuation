package com.nas.fireevacuation.activity.staff_attendance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.gallery.GalleryActivity
import com.nas.fireevacuation.activity.my_profile.MyProfileActivity
import com.nas.fireevacuation.activity.staff_attendance.adapter.StudentAdapter
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.activity.staff_home.model.students_model.StudentModel
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.view.View


class AbsentStudentActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var homeButton: ImageView
    lateinit var backButton: ImageView
    lateinit var myProfile: ImageView
    lateinit var gallery: ImageView
    lateinit var search: ImageView
    lateinit var className: TextView
    lateinit var date: TextView
    lateinit var subject: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var studentList: ArrayList<Lists>
    var progressBarDialog: ProgressBarDialog? = null
    var tabLayout: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absent_student)
        context = this
        homeButton = findViewById(R.id.homeButton)
        backButton = findViewById(R.id.back_button)
        gallery = findViewById(R.id.gallery)
        search = findViewById(R.id.search)
        tabLayout = findViewById(R.id.tabLayout)
        myProfile = findViewById(R.id.myProfile)
        date = findViewById(R.id.date)
        subject = findViewById(R.id.subject)
        className = findViewById(R.id.className)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("ALL"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("PRESENT"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("ABSENT"));
        val tab = tabLayout!!.getTabAt(2)
        tab!!.select()
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> {
                        val intent = Intent(context, AllStudentsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                    1 -> {
                        val intent = Intent(context, PresentStudentsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                    2 -> {
                        val intent = Intent(context, AbsentStudentActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = current.format(formatter)
        date.text = formatted
        className.text = PreferenceManager.getClassName(context)
        subject.text = PreferenceManager.getSubject(context)
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
        recyclerView = findViewById(R.id.recyclerView)
        progressBarDialog = ProgressBarDialog(context!!)
        var studentsResponse: StudentModel
        var studentsArrayList: ArrayList<Lists> = ArrayList()
        var presentStudentList: ArrayList<Lists> = ArrayList()
        var absentStudentList: ArrayList<Lists> = ArrayList()
        var i: Int = 0
        val call: Call<StudentModel> = ApiClient.getClient.studentsAPICall(
            PreferenceManager.getAccessToken(context),
            PreferenceManager.getClassID(context)
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<StudentModel> {
            override fun onResponse(call: Call<StudentModel>, response: Response<StudentModel>) {
                progressBarDialog!!.hide()
                if (!response.body()!!.equals("")) {
                    studentsResponse = response.body()!!
                    if (studentsResponse.responsecode.equals("100")) {
                        if (studentsResponse.message.equals("success")) {
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
//                            try{
//
//                            }catch (e:Exception){
//
//                            }
                            Log.e("Error",absentStudentList.toString())
                            val studentAdapter = StudentAdapter(context!!, absentStudentList,"ABSENT")
                            recyclerView.hasFixedSize()
                            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            recyclerView.adapter = studentAdapter
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
        val intent = Intent(context, StaffHomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }
}