package com.nas.fireevacuation.activity.staff_attendance.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_home.model.student_attendance_model.StudentAttendanceModel
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentAdapter(var context: Context, var studentList: ArrayList<Lists>, var flag: String): RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {
    var absentList: ArrayList<Lists> = PreferenceManager.getAbsentList(context)
    var presentList: ArrayList<Lists> = PreferenceManager.getPresentList(context)
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var studentImage: ImageView? = null
        var studentName: TextView? = null
        var studentID: TextView? = null
        var absentOrPresent: TextView? = null
        var switch: Switch? = null

        init {
            studentImage = itemView.findViewById<View>(R.id.studentImage) as ImageView?
            studentName = itemView.findViewById<View>(R.id.studentName) as TextView?
            studentID = itemView.findViewById<View>(R.id.studentID) as TextView?
            absentOrPresent = itemView.findViewById<View>(R.id.presentOrAbsent) as TextView?
            switch = itemView.findViewById<View>(R.id.switch1) as Switch
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(studentList[holder.adapterPosition].photo).into(holder.studentImage!!)
        holder.studentName!!.text = studentList[holder.adapterPosition].name
        holder.studentID!!.text = studentList[holder.adapterPosition].id
        if (studentList[holder.adapterPosition].present == "1") {
            holder.absentOrPresent!!.text = "P"
            holder.switch!!.isChecked = true
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
        } else {
            holder.absentOrPresent!!.text = "A"
            holder.switch!!.isChecked = false
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))
        }
        holder.switch!!.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                holder.absentOrPresent!!.text = "P"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
                if (!absentList.isEmpty()) {

                        var studentsResponse: StudentAttendanceModel

                        var i = 0
                        val call: Call<StudentAttendanceModel> =
                            ApiClient.getClient.attendanceUpdate(
                                PreferenceManager.getAccessToken(context),
                                studentList[holder.adapterPosition].id.toString(),"1"
                            )
                        call.enqueue(object : Callback<StudentAttendanceModel> {
                            override fun onResponse(
                                call: Call<StudentAttendanceModel>,
                                response: Response<StudentAttendanceModel>
                            ) {
                                if (!response.body()!!.equals("")) {
                                    studentsResponse = response.body()!!
                                    Log.e("Response", response.body().toString())
                                    if (studentsResponse.responsecode.equals("100")) {
                                        if (studentsResponse.message.equals("success")) {
                                            Log.e(
                                                "Success", studentsResponse.toString())
                                            if (flag.equals("Present")) {
                                                studentList.removeAt(holder.adapterPosition)
                                                notifyItemChanged(holder.adapterPosition)
                                            }

//                                            if (!absentList.contains(studentList[holder.adapterPosition])) {
//                                                studentList[holder.adapterPosition].present = "1"
//                                                absentList.remove(studentList[holder.adapterPosition])
//                                            }
//                                            if (!presentList.contains(studentList[holder.adapterPosition])) {
//                                                studentList[holder.adapterPosition].present = "1"
//                                                presentList.add(studentList[holder.adapterPosition])
//                                            }
////                    presentList.add(studentList[position])
//                                            notifyItemChanged(holder.adapterPosition)
//                                            PreferenceManager.setAbsentList(context, absentList)
//                                            PreferenceManager.setPresentList(context, presentList)
                                        }
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<StudentAttendanceModel>,
                                t: Throwable
                            ) {
                                TODO("Not yet implemented")
                            }

                        })




                    }

            } else{
                holder.absentOrPresent!!.text = "A"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))

                        var studentsResponse: StudentAttendanceModel
                        var i = 0
                    Log.e("id", studentList[holder.adapterPosition].id)

                    val call: Call<StudentAttendanceModel> =
                            ApiClient.getClient.attendanceUpdate(
                                PreferenceManager.getAccessToken(context),
                                studentList[holder.adapterPosition].id,
                                "0"
                            )
                        call.enqueue(object : Callback<StudentAttendanceModel> {
                            override fun onResponse(
                                call: Call<StudentAttendanceModel>,
                                response: Response<StudentAttendanceModel>
                            ) {
                                Log.e("Change",response.toString())
                                if (!response.body()!!.equals("")) {
                                    studentsResponse = response.body()!!
                                    Log.e("Response", response.body().toString())
                                    if (studentsResponse.responsecode.equals("100")) {
                                        if (studentsResponse.message.equals("success")) {
                                            Log.e(
                                                "Success", studentsResponse.toString())
                                            if (flag.equals("Present")) {
                                                studentList.removeAt(holder.adapterPosition)
                                                notifyItemChanged(holder.adapterPosition)
                                            }
//                                            studentList.removeAt(holder.adapterPosition)
//                                            notifyItemRemoved(holder.adapterPosition)
//                                            if (!absentList.contains(studentList[holder.adapterPosition])) {
//                                                studentList[holder.adapterPosition].present = "0"
//                                                absentList.add(studentList[holder.adapterPosition])                    }
//                                            if (!presentList.contains(studentList[holder.adapterPosition])) {
//                                                studentList[holder.adapterPosition].present = "0"
//                                                presentList.remove(studentList[holder.adapterPosition])
//                                            }
//                                            notifyItemChanged(holder.adapterPosition)
//                                            PreferenceManager.setAbsentList(context, absentList)
//                                            PreferenceManager.setPresentList(context, presentList)
                                        }
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<StudentAttendanceModel>,
                                t: Throwable
                            ) {
                                TODO("Not yet implemented")
                            }

                        })




//                notifyItemChanged(position)
            }
//            try {
//                if (isChecked) {
//                    Log.e("Absent1",absentList.toString())
//                    Log.e("Present1",presentList.toString())
//                var i = 0
//                    holder.absentOrPresent!!.text = "P"
//                    holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
//                while (i< studentList.size) {
//                    if (absentList.isEmpty()){
//                        absentList = ArrayList()
//                    }
//                    if (presentList.isEmpty()){
//                        presentList = ArrayList()
//                    }
//                    if (studentList[position].id.equals(presentList[i].id)) {
//                        presentList.add(studentList[position])
//                        studentList[position].present = "1"
//                    }
//                    if (studentList[position].id.equals(absentList[i])) {
//                        absentList.remove(absentList[i])
//                    }
//                    PreferenceManager.setPresentList(context,presentList)
//                    PreferenceManager.setAbsentList(context,absentList)
//                    notifyDataSetChanged()
//                    i++
//                }
//            } else {
//                Log.e("Abesnt2",absentList.toString())
//                Log.e("Present2",presentList.toString())
//                var i = 0
//                    holder.absentOrPresent!!.text = "A"
//                    holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))
//                while (i< studentList.size) {
//                    if (absentList.isEmpty()){
//                        absentList = ArrayList()
//                    }
//                    if (presentList.isEmpty()){
//                        presentList = ArrayList()
//                    }
//                    if (studentList[position].id.equals(absentList[i].id)) {
//                        absentList.add(studentList[position])
//                    }
//                    if (studentList[position].id.equals(presentList[i].id)) {
//                        presentList.remove(presentList[i])
//                        Log.e("Present is removed",presentList[i].toString())
//                    }
//                    PreferenceManager.setPresentList(context,presentList)
//                    PreferenceManager.setAbsentList(context,absentList)
//                    notifyDataSetChanged()
//                    i++
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("Error", e.toString())
//            }
        }
    }

    override fun getItemCount(): Int {
        Log.e("List Size",studentList.size.toString())
        return studentList!!.size
    }
//    private fun filter(text: String) {
//        val filteredList: java.util.ArrayList<ResponseArray> = java.util.ArrayList<ResponseArray>()
//        for (item in Response) {
//            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(item)
//            }
//        }
//        adapter = ProductAdapter(filteredList, this@MainActivity)
//        recyclerView.setAdapter(adapter)
//    }
}