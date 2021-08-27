package com.nas.fireevacuation.activity.staff_attendance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_attendance.adapter.ChildRecyclerAdapter
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.common.constants.PreferenceManager


class AbsentStudentsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var studentList: ArrayList<Lists>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_absent_students, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        studentList = PreferenceManager.getAbsentList(context!!)
        studentList = ArrayList()
        val studentAdapter = ChildRecyclerAdapter(context!!, studentList)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = studentAdapter
        Log.e("Student List", studentList.toString())
        return view
    }



}