package com.nas.fireevacuation.activity.evacutation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.create_account.model.CreateAccountModel
import com.nas.fireevacuation.activity.evacutation.model.EvacuationModel
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
import com.google.firebase.database.DatabaseError

import androidx.annotation.NonNull

import com.google.firebase.firestore.auth.User

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists


class EvacuationActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var firebaseID: String
    lateinit var firebaseReference: String
    lateinit var studentList: ArrayList<EvacuationStudentModel>
    lateinit var studentItem: EvacuationStudentModel
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evacuation)
        context = this
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        studentList = ArrayList()
        studentItem = EvacuationStudentModel()
        progressBarDialog = ProgressBarDialog(context)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.add(AllStudentsFragment(), "ALL")
        viewPagerAdapter.add(PresentStudentsFragment(), "PRESENT")
        viewPagerAdapter.add(AbsentStudentsFragment(), "ABSENT")
        viewPager!!.adapter = viewPagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        firebaseID = String()
        firebaseReference = String()
        evacuationCall()
        val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations").child(firebaseReference.toString()).child("students")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("DataBase", snapshot.getValue(EvacuationStudentModel::class.java).toString())
                studentList.clear()
                for (snapshot in snapshot.children) {
                    Log.e("DataBase", snapshot.toString())
                    studentList.add(snapshot.getValue(EvacuationStudentModel::class.java)!!)
                }
                Log.e("Evac List",studentList.toString())
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
