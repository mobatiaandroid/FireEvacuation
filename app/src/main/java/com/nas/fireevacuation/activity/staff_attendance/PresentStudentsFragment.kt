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
import com.nas.fireevacuation.activity.staff_attendance.adapter.StudentAdapter
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.activity.staff_home.model.students_model.StudentModel
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PresentStudentsFragment : Fragment() {
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
//        val view: View = inflater.inflate(R.layout.fragment_present_students, container, false)
//        var i = 0
//        val sortedList: ArrayList<SortModel> = ArrayList()
//        sortedList.add(SortModel("",ArrayList()))
//        parentRecyclerView = view.findViewById(R.id.parentRecyclerView)
//        studentList = PreferenceManager.getPresentList(context!!)
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
lateinit var recyclerView: RecyclerView
    lateinit var studentList: ArrayList<Lists>
    var progressBarDialog: ProgressBarDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_students, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBarDialog = ProgressBarDialog(context!!)
//        studentList = PreferenceManager.getAbsentList(context!!)
        var studentsResponse: StudentModel
        var studentsArrayList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.students_model.Lists> = ArrayList()
        var presentStudentList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.students_model.Lists> = ArrayList()
        var absentStudentList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.students_model.Lists> = ArrayList()
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
                    Log.e("Response",response.body().toString())
                    if (studentsResponse.responsecode.equals("100")) {
                        if (studentsResponse.message.equals("success")) {
                            Log.e("Response",studentsResponse.data.toString())
                            while (i<studentsResponse.data.lists.size) {
                                studentsArrayList.add(studentsResponse.data.lists[i])
                                i++
                            }
                            i = 0
                            while (i<studentsArrayList.size){
                                if (studentsArrayList[i].present.equals("1")) {
                                    presentStudentList.add(studentsArrayList[i])
                                } else {
                                    absentStudentList.add(studentsArrayList[i])

                                }
                                i++
                            }
                            val studentAdapter = StudentAdapter(context!!, presentStudentList)
                            recyclerView.hasFixedSize()
                            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            recyclerView.adapter = studentAdapter

                        }
                    }
                }
            }

            override fun onFailure(call: Call<StudentModel>, t: Throwable) {
                progressBarDialog!!.hide()
            }

        })

        return view
    }
}