package com.nas.fireevacuation.activity.staff_attendance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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


class AbsentStudentsFragment : Fragment() {

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
        var studentsResponse: StudentModel
        var studentsArrayList: ArrayList<Lists> = ArrayList()
        var presentStudentList: ArrayList<Lists> = ArrayList()
        var absentStudentList: ArrayList<Lists> = ArrayList()
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
//                    Log.e("Response",response.body().toString())
                    if (studentsResponse.responsecode.equals("100")) {
                        if (studentsResponse.message.equals("success")) {
//                            Log.e("Response",studentsResponse.data.toString())
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
                            val studentAdapter = StudentAdapter(context!!, absentStudentList,"ABSENT")
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



