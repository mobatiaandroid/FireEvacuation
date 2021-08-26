package com.nas.fireevacuation.activity.staff_attendance

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.common.constants.PreferenceManager


class AllStudentsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var studentList: ArrayList<Lists>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_students, container, false)

/*
        studentList = PreferenceManager.getStudentList(context)
*/
        return view
    }


}