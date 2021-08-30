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
import com.nas.fireevacuation.activity.staff_attendance.adapter.ParentRecyclerAdapter
import com.nas.fireevacuation.activity.staff_home.model.sort_model.SortModel
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.common.constants.PreferenceManager


class AbsentStudentsFragment : Fragment() {
//    lateinit var parentRecyclerView: RecyclerView
//    lateinit var childRecyclerView: RecyclerView
//    lateinit var studentList: ArrayList<Lists>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view: View = inflater.inflate(R.layout.fragment_absent_students, container, false)
//        var i = 0
//        val sortedList: ArrayList<SortModel> = ArrayList()
//        sortedList.add(SortModel("",ArrayList()))
//        parentRecyclerView = view.findViewById(R.id.parentRecyclerView)
//        studentList = PreferenceManager.getAbsentList(context!!)
//        i=0
//        while (i<studentList.size){
//            if (studentList[i].name[0].toString().equals("A")){
//                Log.e("Check",studentList[i].toString())
//                sortedList[0].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("B")){
//                sortedList[1].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("C")){
//                sortedList[2].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("D")){
//                sortedList[3].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("E")){
//                sortedList[4].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("F")){
//                sortedList[5].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("G")){
//                sortedList[6].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("H")){
//                sortedList[7].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("I")){
//                sortedList[8].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("J")){
//                sortedList[9].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("K")){
//                sortedList[10].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("L")){
//                sortedList[11].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("M")){
//                sortedList[12].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("N")){
//                sortedList[13].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("O")){
//                sortedList[14].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("P")){
//                sortedList[15].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("Q")){
//                sortedList[16].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("R")){
//                sortedList[17].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("S")){
//                sortedList[18].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("T")){
//                sortedList[19].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("U")){
//                sortedList[20].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("V")){
//                sortedList[21].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("W")){
//                sortedList[22].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("X")){
//                sortedList[23].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("Y")){
//                sortedList[24].list.add(studentList[i])
//            } else if (studentList[i].name[0].toString().equals("Z")){
//                sortedList[25].list.add(studentList[i])
//            }
//            i++
//        }
//
//        var j = 0
//        while (j < 26) {
//            val parentRecyclerAdapter = ParentRecyclerAdapter(context!!, sortedList[i].list)
//            parentRecyclerView.hasFixedSize()
//            parentRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            parentRecyclerView.adapter = parentRecyclerAdapter
//            j++
//        }
//        return view
//    }
}



