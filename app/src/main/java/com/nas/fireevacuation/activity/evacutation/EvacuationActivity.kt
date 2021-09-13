package com.nas.fireevacuation.activity.evacutation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.adapter.StudentEvacuationAdapter
import com.nas.fireevacuation.activity.evacutation.model.evacuation_model.EvacuationModel
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.activity.staff_attendance.adapter.ViewPagerAdapter
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class EvacuationActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var firebaseID: String
    lateinit var firebaseReference: String
    lateinit var studentList: ArrayList<EvacuationStudentModel>
    lateinit var className: TextView
    lateinit var date: TextView
    lateinit var absentEvac: ArrayList<EvacuationStudentModel>
    lateinit var presentEvac: ArrayList<EvacuationStudentModel>
    lateinit var subject: TextView

        lateinit var recyclerView: RecyclerView
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evacuation)
        context = this
        studentList = ArrayList()
        tabLayout = findViewById(R.id.tabLayout)
//        viewPager = findViewById(R.id.viewPager)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        progressBarDialog = ProgressBarDialog(context)
        date = findViewById(R.id.date)
        className = findViewById(R.id.className)
        absentEvac = ArrayList()
        presentEvac = ArrayList()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = current.format(formatter)
        date.text = formatted
        className.text = PreferenceManager.getClassName(context)
        firebaseID = String()
        var assemblyPointID = ""
        var classID = ""
        var studentSnap = ""
        var staffID = ""
        var name = ""
        var query: Query
        var students: Map<String,EvacuationStudentModel> = mapOf()
        var student = ""
        firebaseReference = String()
        evacuationCall()
        recyclerView.hasFixedSize()
        recyclerView.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )

        )
        val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                databaseReference.child("-MjTySeMZHiwcRleuOcS").addValueEventListener(object: ValueEventListener{
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
                            )
                            Log.e("ClassIDValue errorcheck",snapshot.child("present").toString())
                            Log.e("ClassIDValue errorcheck1",snapshot.toString())
                            if ((snapshot.child("class_id").value)!!.equals(PreferenceManager.getClassID(context))){
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
                                Log.e("Students added", studentItem.toString())
                                if (!studentList.contains(studentItem)) {
                                    studentList.add(studentItem)
                                }
                            }
                        }
                        Log.e("Students1",studentList.toString())
                        val adapter = StudentEvacuationAdapter(context, studentList)
                        recyclerView.adapter = adapter
//                        var i = 0
//                        while (i<studentList.size){
//                            if (studentList[i].found.equals("0")) {
//                                if (!absentEvac.contains(studentList[i])) {
//                                    absentEvac.add(studentList[i])
//                                }
//                            } else {
//                                if (!presentEvac.contains(studentList[i])) {
//                                    presentEvac.add(studentList[i])
//                                }
//                            }
//                            i++
//                        }
//                        PreferenceManager.setEvacStudentList(context,studentList)
//                        PreferenceManager.setNotFoundList(context,absentEvac)
//                        PreferenceManager.setFoundList(context,presentEvac)
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            }
            override fun onCancelled(error: DatabaseError) {}
        })


//        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
//        viewPagerAdapter.add(AllEvacuationFragment(), "ALL")
//        viewPagerAdapter.add(FoundEvacuationFragment(), "FOUND")
//        viewPagerAdapter.add(NotFoundEvacuationFragment(), "NOT FOUND")
//        viewPager!!.adapter = viewPagerAdapter
//        tabLayout!!.setupWithViewPager(viewPager)
//        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
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
                        firebaseID = evacuationResponse.data.id.toString()
                        Log.e("fireref",firebaseReference.toString())

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
}
