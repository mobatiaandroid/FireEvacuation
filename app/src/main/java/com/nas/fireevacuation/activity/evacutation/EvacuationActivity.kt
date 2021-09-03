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
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
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
    lateinit var studentList: ArrayList<EvacuationStudentModel>
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evacuation)
        context = this
        studentList = ArrayList()
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        progressBarDialog = ProgressBarDialog(context)
//        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
//        viewPagerAdapter.add(AllStudentsFragment(), "ALL")
//        viewPagerAdapter.add(PresentStudentsFragment(), "PRESENT")
//        viewPagerAdapter.add(AbsentStudentsFragment(), "ABSENT")
//        viewPager!!.adapter = viewPagerAdapter
//        tabLayout!!.setupWithViewPager(viewPager)
//        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
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
        val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
        Log.e("databaseref", databaseReference.toString())
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("Output0",snapshot.toString())
                Log.e("Output1",snapshot.child("-MifUGjqDwIm397no97D").toString())
                Log.e("Firebaseref", firebaseReference)
//                Log.e("Output2",snapshot.child(firebaseReference).child("staff_id").value.toString())
//                Log.e("Firebaseref", firebaseReference)
//                Log.e("Output3",snapshot.child(firebaseReference).child("assembly_point_id").value.toString())
//                Log.e("Firebaseref", firebaseReference)
//
//                Log.e("Output4",snapshot.child(firebaseReference).child("students").value.toString())
                databaseReference.child("-MifUGjqDwIm397no97D").addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snapshot in snapshot.children){
                            var studentItem: EvacuationStudentModel = EvacuationStudentModel("",
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
                                "")
                            Log.e("item added",snapshot.child("id").value.toString())
                            if ((snapshot.child("class_id").value)!!.equals(PreferenceManager.getClassID(context))){
                                studentItem.id = snapshot.child("id").value.toString()
                                studentItem.name = snapshot.child("student_name").value.toString()
                                studentItem.photo = snapshot.child("photo").value.toString()
                                studentItem.found = snapshot.child("found").value.toString()
                                studentList.add(studentItem)
                                Log.e("item added",studentList.toString())
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
//                classID = snapshot.child("class_id").value.toString()
//                staffID = snapshot.child("staff_id").value.toString()
//                assemblyPointID = snapshot.child("assembly_point_id").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        Log.e("Student Evac", studentList.toString())


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
