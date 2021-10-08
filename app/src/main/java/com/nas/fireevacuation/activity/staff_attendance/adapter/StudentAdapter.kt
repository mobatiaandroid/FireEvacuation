package com.nas.fireevacuation.activity.staff_attendance.adapter

import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
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
//    var absentList: ArrayList<Lists> = PreferenceManager.getAbsentList(context)
//    var presentList: ArrayList<Lists> = PreferenceManager.getPresentList(context)

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var studentImage: ImageView? = null
        var studentName: TextView? = null
        var studentID: TextView? = null
        var absentOrPresent: TextView? = null
        var switch: Switch? = null
        var loader: ProgressBar? = null


        init {
            studentImage = itemView.findViewById<View>(R.id.studentImage) as ImageView?
            studentName = itemView.findViewById<View>(R.id.studentName) as TextView?
            studentID = itemView.findViewById<View>(R.id.studentID) as TextView?
            absentOrPresent = itemView.findViewById<View>(R.id.presentOrAbsent) as TextView?
            switch = itemView.findViewById<View>(R.id.switch1) as Switch
            loader = itemView.findViewById<View>(R.id.loader) as ProgressBar
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
        holder.switch!!.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked){
                holder.absentOrPresent!!.text = "P"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
                        var studentsResponse: StudentAttendanceModel
                        var i = 0
                        val call: Call<StudentAttendanceModel> =
                            ApiClient.getClient.attendanceUpdate(
                                PreferenceManager.getAccessToken(context),
                                studentList[holder.adapterPosition].id,"1"
                            )
                    holder.loader!!.visibility = View.VISIBLE
                        call.enqueue(object : Callback<StudentAttendanceModel> {
                            override fun onResponse(
                                call: Call<StudentAttendanceModel>,
                                response: Response<StudentAttendanceModel>
                            ) {
                                holder.loader!!.visibility = View.GONE

                                if (!response.body()!!.equals("")) {
                                    studentsResponse = response.body()!!
//                                    Log.e("Response", response.body().toString())
                                    if (studentsResponse.responsecode.equals("100")) {
                                        if (studentsResponse.message.equals("success")) {
//                                            Log.e(
//                                                "Success", studentsResponse.toString())
                                            try {
                                                if (flag.equals("Present")) {
                                                    studentList.removeAt(holder.adapterPosition)
                                                    notifyItemChanged(holder.adapterPosition)
                                                }
                                            }catch (e: Exception){
                                                Log.e("Error",e.toString())
                                            }
                                            try {
                                                if (flag.equals("Absent")) {
                                                    studentList.removeAt(holder.adapterPosition)
                                                    notifyItemChanged(holder.adapterPosition)
                                                }
                                            }catch (e: Exception){
                                                Log.e("Error2",e.toString())
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
                                holder.loader!!.visibility = View.GONE

                            }

                        })






            } else{
                holder.absentOrPresent!!.text = "A"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))

                        var studentsResponse: StudentAttendanceModel
                        var i = 0
//                    Log.e("id", studentList[holder.adapterPosition].id)

                    val call: Call<StudentAttendanceModel> =
                            ApiClient.getClient.attendanceUpdate(
                                PreferenceManager.getAccessToken(context),
                                studentList[holder.adapterPosition].id,
                                "0"
                            )
                holder.loader!!.visibility = View.VISIBLE

                call.enqueue(object : Callback<StudentAttendanceModel> {
                            override fun onResponse(
                                call: Call<StudentAttendanceModel>,
                                response: Response<StudentAttendanceModel>
                            ) {
                                holder.loader!!.visibility = View.GONE

//                                Log.e("Change",response.toString())
                                if (!response.body()!!.equals("")) {
                                    studentsResponse = response.body()!!
//                                    Log.e("Response", response.body().toString())
                                    if (studentsResponse.responsecode.equals("100")) {
                                        if (studentsResponse.message.equals("success")) {
//                                            Log.e(
//                                                "Success", studentsResponse.toString())
                                            if (flag.equals("Present")) {
                                                studentList.removeAt(holder.adapterPosition)
                                                notifyItemChanged(holder.adapterPosition)
                                            }
                                        }
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<StudentAttendanceModel>,
                                t: Throwable
                            ) {
                                holder.loader!!.visibility = View.GONE
                            }

                        })




            }

        }
    }

    override fun getItemCount(): Int {
        Log.e("List Size",studentList.size.toString())
        return studentList!!.size
    }

}