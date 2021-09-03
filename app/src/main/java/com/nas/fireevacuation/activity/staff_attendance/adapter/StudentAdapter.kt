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
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.common.constants.PreferenceManager
import java.lang.Exception

class StudentAdapter(var context: Context, var studentList: ArrayList<Lists>): RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {

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
        var absentList: ArrayList<Lists> = PreferenceManager.getAbsentList(context)
        var presentList: ArrayList<Lists> = PreferenceManager.getPresentList(context)
        if (absentList.isEmpty()){
            absentList = ArrayList()
        }
        if (presentList.isEmpty()){
            presentList = ArrayList()
        }
        Glide.with(context).load(studentList[position].photo).into(holder.studentImage!!)
        holder.studentName!!.text = studentList[position].name
        holder.studentID!!.text = studentList[position].id
        if (studentList[position].present == "1") {
            holder.absentOrPresent!!.text = "P"
            holder.switch!!.isChecked = true
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
        } else {
            holder.absentOrPresent!!.text = "A"
            holder.switch!!.isChecked = false
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))
        }
        holder.switch!!.setOnCheckedChangeListener { buttonView, isChecked ->

            try {
                if (isChecked) {
                    Log.e("Abesnt",absentList.toString())
                    Log.e("Present",presentList.toString())
                var i = 0
                    holder.absentOrPresent!!.text = "P"
                    holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
                while (i< studentList.size) {
                    if (studentList[position].id.equals(presentList[i].id)) {
                        presentList.add(studentList[position])
                        studentList[position].present = "1"
                    }
                    if (studentList[position].id.equals(absentList[i])) {
                        absentList.remove(absentList[i])
                    }
                    PreferenceManager.setPresentList(context,presentList)
                    PreferenceManager.setAbsentList(context,absentList)
                    notifyDataSetChanged()
                    i++
                }
            } else {
                Log.e("Abesnt",absentList.toString())
                Log.e("Present",presentList.toString())
                var i = 0
                    holder.absentOrPresent!!.text = "A"
                    holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))
                while (i< studentList.size) {
                    if (studentList[position].id.equals(absentList[i].id)) {
                        absentList.add(studentList[position])
                    }
                    if (studentList[position].id.equals(presentList[i].id)) {
                        presentList.remove(presentList[i])
                    }
                    PreferenceManager.setPresentList(context,presentList)
                    PreferenceManager.setAbsentList(context,absentList)
                    notifyDataSetChanged()
                    i++
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        Log.e("List Size",studentList.size.toString())
        return studentList!!.size
    }
}