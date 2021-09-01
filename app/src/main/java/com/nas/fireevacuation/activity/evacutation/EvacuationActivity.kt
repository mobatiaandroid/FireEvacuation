package com.nas.fireevacuation.activity.evacutation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.model.evacuation_model.EvacuationModel
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.StudentX
import com.nas.fireevacuation.activity.staff_attendance.AbsentStudentsFragment
import com.nas.fireevacuation.activity.staff_attendance.AllStudentsFragment
import com.nas.fireevacuation.activity.staff_attendance.PresentStudentsFragment
import com.nas.fireevacuation.activity.staff_attendance.adapter.ViewPagerAdapter
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EvacuationActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var firebaseID: String
    lateinit var firebaseReference: String
    lateinit var studentList: HashMap<String,StudentX>
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evacuation)
        context = this
        studentList = HashMap()
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        progressBarDialog = ProgressBarDialog(context)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.add(AllStudentsFragment(), "ALL")
        viewPagerAdapter.add(PresentStudentsFragment(), "PRESENT")
        viewPagerAdapter.add(AbsentStudentsFragment(), "ABSENT")
        viewPager!!.adapter = viewPagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        firebaseID = String()
        var assemblyPointID = ""
        var classID = ""
        var studentSnap = ""
        var staffID = ""
        var name = ""
        var query: Query
        var students: Map<String,StudentX> = mapOf()
        var student = ""
        firebaseReference = String()
        evacuationCall()
        val evacuationRef = FirebaseDatabase.getInstance().reference.child("evacuations")
        val currentRef = evacuationRef.child(firebaseReference)
        currentRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("Snap Value", snapshot.toString())
                if (snapshot.value != null) {
                    for (snapshot in snapshot.children) {
                        Log.e("Snap Value", snapshot.value.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
//        databaseReference.addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.value !=null) {
//                    for (snapshot in snapshot.children) {
//                        classID = snapshot.child("class_id").value.toString()
//                        staffID = snapshot.child("staff_id").value.toString()
//                        assemblyPointID = snapshot.child("assembly_point_id").value.toString()
//                        student = snapshot.child("students").toString()
//
//                    }
//                    Log.e("snapvalues", name)
//                    Log.e("student", student)
//
//                }
//                assemblyPointID = snapshot.child("assembly_point_id").value.toString()
//                Log.e("Assembly Point", snapshot.toString())
//                Log.e("Size", databaseList().size.toString())
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })



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
                        Log.e("Firebaseref", firebaseReference)
                        firebaseID = evacuationResponse.data.id.toString()
                    }
                }
            }

            override fun onFailure(call: Call<EvacuationModel>, t: Throwable) {
                progressBarDialog.hide()
            }
        })
    }
}
