package com.nas.fireevacuation.activity.evacutation


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.adapter.SearchAdapter
import com.nas.fireevacuation.activity.evacutation.adapter.StudentEvacuationAdapter
import com.nas.fireevacuation.activity.evacutation.model.evacuation_model.EvacuationModel
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.activity.staff_attendance.AbsentStudentActivity
import com.nas.fireevacuation.activity.staff_attendance.AllStudentsActivity
import com.nas.fireevacuation.activity.staff_attendance.PresentStudentsActivity
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


open class NotFoundActivity() : AppCompatActivity() {
    lateinit var context: Context
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var firebaseID: String
    lateinit var firebaseReference: String
    lateinit var closeButton: ImageView
        lateinit var studentList: ArrayList<EvacuationStudentModel>
    lateinit var className: TextView
    lateinit var date: TextView
    lateinit var searchIcon: ImageView
    lateinit var searchView: View
    lateinit var searchText: EditText
    lateinit var searchClose: ImageView
    lateinit var searchRecyclerView: RecyclerView
    lateinit var header: TextView
    lateinit var absentEvac: ArrayList<EvacuationStudentModel>
    lateinit var presentEvac: ArrayList<EvacuationStudentModel>
    lateinit var subject: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var mDatabaseReference: DatabaseReference
//    lateinit var firebaseAdapter: FirebaseAdapter

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evacuation)
        context = this
        studentList = ArrayList()
        tabLayout = findViewById(R.id.tabLayout)
        searchIcon = findViewById(R.id.searchIcon)
        searchView = findViewById(R.id.searchView)
        searchText = findViewById(R.id.searchText)
        searchClose = findViewById(R.id.searchClose)
        header = findViewById(R.id.header)
        recyclerView = findViewById(R.id.recyclerView)
        searchRecyclerView = findViewById(R.id.searchRecyclerView)
        progressBarDialog = ProgressBarDialog(context)
        date = findViewById(R.id.date)
        className = findViewById(R.id.className)
        subject = findViewById(R.id.subject)
        closeButton = findViewById(R.id.close)
        absentEvac = ArrayList()
        presentEvac = ArrayList()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = current.format(formatter)
        date.text = formatted
        subject.text = PreferenceManager.getSubject(context)
        className.text = PreferenceManager.getClassName(context)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("ALL"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("NOT FOUND"))
        val tab = tabLayout!!.getTabAt(1)
        tab!!.select()
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                try {
                    when(tab!!.position){
                        0 -> {
                            val intent = Intent(context, EvacuationActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                        }
                        1 -> {
                            val intent = Intent(context, NotFoundActivity::class.java)
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
        searchIcon.setOnClickListener {
            searchView.visibility = View.VISIBLE
            header.visibility = View.GONE
            searchIcon.visibility = View.GONE
            closeButton.visibility = View.GONE
        }
        searchClose.setOnClickListener {
            closeKeyboard()
            searchView.visibility = View.GONE
            searchRecyclerView.visibility = View.GONE
            header.visibility = View.VISIBLE
            searchIcon.visibility = View.VISIBLE
            closeButton.visibility = View.VISIBLE
            val searchAdapter = SearchAdapter(context, ArrayList())
            searchRecyclerView.adapter = searchAdapter
            val adapter = StudentEvacuationAdapter(context, studentList)
            recyclerView.adapter = adapter
            searchText.text.clear()


        }
        closeButton.setOnClickListener {
            val intent = Intent(context, StaffHomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length > 2) {
                    searchFilter(s.toString())
                    val searchAdapter = SearchAdapter(context, ArrayList())
                    searchRecyclerView.adapter = searchAdapter

                }
                if (s.isEmpty()) {
                    val searchAdapter = SearchAdapter(context, ArrayList())
                    searchRecyclerView.adapter = searchAdapter
                    searchRecyclerView.visibility = View.GONE
                }
            }

        })
        firebaseID = String()
        var assemblyPointID = ""
        var classID = ""
        var studentSnap = ""
        var staffID = ""
        var name = ""
        var query: Query
        var students: Map<String, EvacuationStudentModel> = mapOf()
        var student = ""
        var f = 1
        firebaseReference = String()
        evacuationCall()
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("evacuations")

//        val queries: Query = mDatabaseReference.child(PreferenceManager.getFireRef(context))
//            .child("students").orderByChild("class_id").startAt(PreferenceManager.getClassID(context).toString())
//
//        Log.e("Query", queries.toString())
//        var options: FirebaseRecyclerOptions<EvacuationStudentModel> = FirebaseRecyclerOptions.Builder<EvacuationStudentModel>()
//            .setQuery(queries, EvacuationStudentModel::class.java)
//            .build()
//
//        Log.e("Options",options.toString())
//        Log.e("Query", options.toString())
//        firebaseAdapter = FirebaseAdapter(options)
//        recyclerView.adapter = firebaseAdapter
        val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                databaseReference.child(firebaseReference).child("students")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (snapshot in snapshot.children) {
                                var studentItem: EvacuationStudentModel = EvacuationStudentModel(
                                    "", "", "", "",
                                    "", "", "", "",
                                    "", "", "", "",
                                    "", "", "", "",
                                    "", ""
                                )

                                if ((snapshot.child("class_id").value)!!.equals(
                                        PreferenceManager.getClassID(
                                            context
                                        )
                                    )
                                ) {
                                    studentItem.id = snapshot.child("id").value.toString()
                                    studentItem.student_name =
                                        snapshot.child("student_name").value.toString()
                                    studentItem.photo = snapshot.child("photo").value.toString()
                                    studentItem.found = snapshot.child("found").value.toString()
                                    studentItem.class_id =
                                        snapshot.child("class_id").value.toString()
                                    studentItem.assembly_point =
                                        snapshot.child("assembly_point").value.toString()
                                    studentItem.assembly_point_id =
                                        snapshot.child("assembly_point_id").value.toString()
                                    studentItem.created_at =
                                        snapshot.child("created_at").value.toString()
                                    studentItem.present = snapshot.child("present").value.toString()
                                    studentItem.registration_id =
                                        snapshot.child("registration_id").value.toString()
                                    studentItem.staff_id =
                                        snapshot.child("staff_id").value.toString()
                                    studentItem.staff_name =
                                        snapshot.child("staff_name").value.toString()
                                    studentItem.section = snapshot.child("section").value.toString()
                                    studentItem.updated_at =
                                        snapshot.child("updated_at").value.toString()
                                    studentItem.created_at =
                                        snapshot.child("created_at").value.toString()
                                    studentItem.class_name =
                                        snapshot.child("class_name").value.toString()
                                    studentItem.created_by =
                                        snapshot.child("created_by").value.toString()
                                    studentItem.updated_by =
                                        snapshot.child("updated_by").value.toString()

                                    var i = 0
                                    var found = 0
                                    while (i<studentList.size){
                                        if (studentList[i].id.equals(studentItem.id)){
                                            found = 1
                                            break
                                        } else{
                                            found = 0
                                        }
                                        i++
                                    }
                                    if (found == 0){
//                                        if (!studentList.contains(studentItem)) {
                                            if (studentItem.present.equals("1")){
                                                studentList.add(studentItem)
                                            }
//                                        }
                                    }
                                }
                            }
                            Log.e("Students1", studentList.toString())
                            var swapped: Boolean = true
                            var i = 0
                            studentList.sortBy {
                                it.student_name
                            }
//                            PreferenceManager.setEvacStudentList(context, studentList)
                            val adapter = StudentEvacuationAdapter(context, studentList)
                            Log.e("Student 1 2 3List", studentList.toString())
                            recyclerView.adapter = adapter
                            recyclerView.scrollToPosition(CommonMethods.getPos() - 2)
                            recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->


                                if (searchRecyclerView.isVisible){
                                    searchRecyclerView.visibility = View.GONE
                                }

                            }

                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }


    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    private fun searchFilter(searchString: String) {
        val queries: Query = mDatabaseReference.child(PreferenceManager.getFireRef(context))
            .child("students")
            .orderByChild("student_name")
            .endAt(searchString+"\uf8ff");
        queries.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("Result", snapshot.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
//
//        Log.e("Query", queries.toString())
//        var options: FirebaseRecyclerOptions<EvacuationStudentModel> = FirebaseRecyclerOptions.Builder<EvacuationStudentModel>()
//            .setQuery(queries, EvacuationStudentModel::class.java)
//            .build()
//
//        Log.e("Options",options.toString())
//        Log.e("Query", options.toString())
//        firebaseAdapter = FirebaseAdapter(options)
//        recyclerView.adapter = firebaseAdapter
        var studentSearchList: ArrayList<EvacuationStudentModel> = ArrayList()
        var filteredList: ArrayList<EvacuationStudentModel> = ArrayList()
        val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                databaseReference.child(firebaseReference).child("students").addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snapshot in snapshot.children){
                            var studentItem: EvacuationStudentModel = EvacuationStudentModel(
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            )
                            studentItem.id = snapshot.child("id").value.toString()
                            studentItem.student_name = snapshot.child("student_name").value.toString()
                            studentItem.photo = snapshot.child("photo").value.toString()
                            studentItem.found = snapshot.child("found").value.toString()
                            studentItem.class_id = snapshot.child("class_id").value.toString()
                            studentItem.assembly_point = snapshot.child("assembly_point").value.toString()
                            studentItem.assembly_point_id = snapshot.child("assembly_point_id").value.toString()
                            studentItem.created_at = snapshot.child("created_at").value.toString()
                            studentItem.present = snapshot.child("present").value.toString()
                            studentItem.registration_id = snapshot.child("registration_id").value.toString()
                            studentItem.staff_id = snapshot.child("staff_id").value.toString()
                            studentItem.staff_name = snapshot.child("staff_name").value.toString()
                            studentItem.section = snapshot.child("section").value.toString()
                            studentItem.updated_at = snapshot.child("updated_at").value.toString()
                            studentItem.created_by = snapshot.child("created_by").value.toString()
                            studentItem.updated_by = snapshot.child("updated_by").value.toString()
//                            Log.e("Students added", studentItem.toString())
                            if (!studentSearchList.contains(studentItem)) {
                                studentSearchList.add(studentItem)
                            }
                        }
                        for (item in studentSearchList) {
                            if (item.student_name.toLowerCase().contains(searchString.toLowerCase()) || item.registration_id.contains(searchString)) {

                                var i = 0
                                var found = 0
                                while (i<filteredList.size){
                                    if (filteredList[i].id.equals(item.id)){
                                        found = 1
                                        break
                                    } else{
                                        found = 0
                                    }
                                    i++
                                }
                                if (found == 0){
//                                        if (!studentList.contains(studentItem)) {
                                    if (item.present.equals("1")){
                                        filteredList.add(item)
                                    }
//                                        }
                                }
//                                if (!filteredList.contains(item)) {
//                                    filteredList.add(item)
//
//                                }
                            }
                            filteredList.sortBy {
                                it.student_name
                            }
                        }
                        if (filteredList.isNotEmpty()){
                            searchRecyclerView.visibility = View.VISIBLE
                            searchRecyclerView.hasFixedSize()
                            searchRecyclerView.layoutManager = LinearLayoutManager(
                                context,
                                LinearLayoutManager.VERTICAL,
                                false
                            )

                        }

                        val adapter = SearchAdapter(context, filteredList)
                        searchRecyclerView.adapter = adapter
                        filteredList = ArrayList()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun evacuationCall() {
        var evacuationResponse: EvacuationModel
        val call: Call<EvacuationModel> = ApiClient.getClient.evacuationStart(
            PreferenceManager.getAccessToken(context),
            PreferenceManager.getStaffID(context),
            PreferenceManager.getClassID(context),
            PreferenceManager.getAssemblyPoint(context)
        )
        progressBarDialog.show()
        call.enqueue(object : Callback<EvacuationModel> {
            override fun onResponse(
                call: Call<EvacuationModel>,
                response: Response<EvacuationModel>){
                progressBarDialog.hide()
                if(!response.body()!!.equals("")) {
                    evacuationResponse = response.body()!!
                    if (evacuationResponse.responsecode.equals("100")) {
                        firebaseReference = evacuationResponse.data.firebase_referance
                        PreferenceManager.setFireRef(context,firebaseReference)
                        firebaseID = evacuationResponse.data.id.toString()
                        Log.e("fireref", firebaseReference)

                    }
                }
            }

            override fun onFailure(call: Call<EvacuationModel>, t: Throwable) {
                progressBarDialog.hide()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(context, StaffHomeActivity::class.java)
        startActivity(intent)
        finish()
    }

//    override fun onStart() {
//        super.onStart()
//        firebaseAdapter.startListening();
//    }
//
//    override fun onStop() {
//        super.onStop()
//        firebaseAdapter.stopListening();
//    }

}

