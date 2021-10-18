package com.nas.fireevacuation.activity.evacutation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.adapter.StudentEvacuationAdapter
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.common.constants.PreferenceManager


class NotFoundEvacuationFragment : Fragment() {


    lateinit var recyclerView: RecyclerView
    lateinit var studentList: ArrayList<EvacuationStudentModel>
//    lateinit var studentList: ArrayList<Lists>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_not_found_evacuation, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        studentList = ArrayList()
//        val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
//        databaseReference.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                databaseReference.child("-MifUGjqDwIm397no97D").addValueEventListener(object:
//                    ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        for (snapshot in snapshot.children){
//                            if (snapshot.child("class_id").value != null) {
//                                var studentItem: EvacuationStudentModel = EvacuationStudentModel("",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                )
//                                if ((snapshot.child("class_id").value)!!.equals(PreferenceManager.getClassID(context))){
//                                    if (snapshot.child("found").value!!.equals("0")) {
//                                        studentItem.id = snapshot.child("id").value.toString()
//                                        studentItem.student_name =
//                                            snapshot.child("student_name").value.toString()
//                                        studentItem.photo = snapshot.child("photo").value.toString()
//                                        studentItem.found = snapshot.child("found").value.toString()
//                                        studentItem.class_id =
//                                            snapshot.child("class_id").value.toString()
//                                        studentList.add(studentItem)
//                                    }
//                                }
//                            } else{
//                                break
//                            }
//
//                        }
//
//                    }
//                    override fun onCancelled(error: DatabaseError) {}
//                })
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        })
        studentList = ArrayList()
        val studentAdapter = StudentEvacuationAdapter(context!!, studentList)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = studentAdapter
        return view
    }
}