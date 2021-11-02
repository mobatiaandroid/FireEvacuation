package com.nas.fireevacuation.activity.staff_attendance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.adapter.SearchAdapter
import com.nas.fireevacuation.activity.evacutation.adapter.StudentEvacuationAdapter
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.activity.gallery.GalleryActivity
import com.nas.fireevacuation.activity.my_profile.MyProfileActivity
import com.nas.fireevacuation.activity.staff_attendance.adapter.StudentAdapter
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.activity.staff_home.model.students_model.StudentModel
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AllStudentsActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var homeButton: ImageView
    lateinit var backButton: ImageView
    lateinit var myProfile: ImageView
    lateinit var gallery: ImageView
    lateinit var className: TextView
    lateinit var date: TextView
    lateinit var subject: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var studentList: ArrayList<Lists>
    lateinit var searchIcon: ImageView
    lateinit var searchView: View
    lateinit var searchText: EditText
    lateinit var searchClose: ImageView
    lateinit var header: TextView
    var progressBarDialog: ProgressBarDialog? = null
    var tabLayout: TabLayout? = null
    var studentsArrayList: ArrayList<Lists> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_students)
        context = this
        homeButton = findViewById(R.id.homeButton)
        backButton = findViewById(R.id.back_button)
        gallery = findViewById(R.id.gallery)
        tabLayout = findViewById(R.id.tabLayout)
        myProfile = findViewById(R.id.myProfile)
        date = findViewById(R.id.date)
        subject = findViewById(R.id.subject)
        className = findViewById(R.id.className)
        searchIcon = findViewById(R.id.searchIcon)
        searchView = findViewById(R.id.searchView)
        searchText = findViewById(R.id.searchText)
        searchClose = findViewById(R.id.searchClose)
        header = findViewById(R.id.header)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("ALL"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("PRESENT"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("ABSENT"));
        searchIcon.setOnClickListener {
            searchView.visibility = View.VISIBLE
            header.visibility = View.GONE
            searchIcon.visibility = View.GONE
            backButton.visibility = View.GONE
        }
        searchClose.setOnClickListener {
            closeKeyboard()
            try {
                searchView.visibility = View.GONE
                header.visibility = View.VISIBLE
                searchIcon.visibility = View.VISIBLE
                backButton.visibility = View.VISIBLE
                val adapter = StudentAdapter(context, studentsArrayList,"ALL")
                recyclerView.adapter = adapter
                searchText.text.clear()
            }catch (e:Exception){
                Log.e("Error",e.toString())
            }

        }
        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length > 1) {
                    searchFilter(s.toString())
                }
            }

        })
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                try {
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
                }catch (e:Exception){
                    Log.e("Error",e.toString())
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

        var i: Int = 0
        if (CommonMethods.isInternetAvailable(context)) {
            val call: Call<StudentModel> = ApiClient.getClient.studentsAPICall(
                PreferenceManager.getAccessToken(context),
                PreferenceManager.getClassID(context)
            )
            progressBarDialog!!.show()
            call.enqueue(object : Callback<StudentModel> {
                override fun onResponse(
                    call: Call<StudentModel>,
                    response: Response<StudentModel>
                ) {
                    progressBarDialog!!.hide()
                    if (!response.body()!!.equals("")) {
                        studentsResponse = response.body()!!
                        if (studentsResponse.responsecode.equals("100")) {
                            if (studentsResponse.message.equals("success")) {
                                while (i < studentsResponse.data.lists.size) {
                                    studentsArrayList.add(studentsResponse.data.lists[i])
                                    i++
                                }
                                Log.e("Studentin All", studentsArrayList.toString())
                                val studentAdapter =
                                    StudentAdapter(context, studentsArrayList, "ALL")
                                recyclerView.hasFixedSize()
                                recyclerView.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                recyclerView.adapter = studentAdapter
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<StudentModel>, t: Throwable) {
                    progressBarDialog!!.hide()
                }

            })
        } else{
            CommonMethods.showLoginErrorPopUp(context,"Check your Internet connection")

        }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }    }

    private fun searchFilter(searchString: String) {
        var studentsResponse: StudentModel
        var studentsArrayList: ArrayList<Lists> = ArrayList()
        var filteredList: ArrayList<Lists> = ArrayList()
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
                            for (item in studentsArrayList) {
                                if (item.name.toLowerCase().contains(searchString.toLowerCase()) || item.registration_id.contains(searchString)) {
                                    if (!filteredList.contains(item)) {
                                        filteredList.add(item)

                                    }
                                }
                                filteredList.sortBy {
                                    it.name
                                }



                            }

                            val adapter = StudentAdapter(context, filteredList,"ALL")
                            recyclerView.adapter = adapter
                            filteredList = ArrayList()
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