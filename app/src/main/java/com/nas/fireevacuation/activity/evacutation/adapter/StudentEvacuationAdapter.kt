package com.nas.fireevacuation.activity.evacutation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.activity.evacutation.model.post.Post
import com.nas.fireevacuation.common.constants.PreferenceManager

class StudentEvacuationAdapter(var context: Context, var studentList: ArrayList<EvacuationStudentModel>): RecyclerView.Adapter<StudentEvacuationAdapter.MyViewHolder>() {
    var absentList: ArrayList<EvacuationStudentModel> = PreferenceManager.getNotFoundList(context)
    var presentList: ArrayList<EvacuationStudentModel> = PreferenceManager.getFoundList(context)
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var studentImage: ImageView? = null
        var studentName: TextView? = null
        var registrationID: TextView? = null
        var absentOrPresent: TextView? = null
        var switch: Switch? = null
        var loader: ProgressBar? = null

        init {
            studentImage = itemView.findViewById<View>(R.id.studentImage) as ImageView?
            studentName = itemView.findViewById<View>(R.id.studentName) as TextView?
            registrationID = itemView.findViewById<View>(R.id.studentID) as TextView?
            absentOrPresent = itemView.findViewById<View>(R.id.presentOrAbsent) as TextView?
            switch = itemView.findViewById<View>(R.id.switch1) as Switch
            loader = itemView.findViewById<View>(R.id.loader) as ProgressBar
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_evacuation_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(studentList[position].photo).into(holder.studentImage!!)
        holder.studentName!!.text = studentList[position].student_name
        holder.registrationID!!.text = studentList[position].registration_id
        if (studentList[position].found == "1") {
            holder.absentOrPresent!!.text = "P"
            holder.switch!!.isChecked = true
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.absentOrPresent!!.text = "A"
            holder.switch!!.isChecked = false
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context, R.color.pink))
        }
        holder.switch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.absentOrPresent!!.text = "P"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
                var child = studentList[holder.adapterPosition]
                studentList[holder.adapterPosition].found = "1"
                holder.loader!!.visibility = View.VISIBLE
                val databaseReference = FirebaseDatabase.getInstance().reference
                    .child("evacuations")
                    .child(PreferenceManager.getFireRef(context))
                databaseReference.child("students")
                    .child(child.id).child("found")
                    .setValue("1").addOnSuccessListener {
                        holder.loader!!.visibility = View.GONE

                    }

            } else {
                holder.absentOrPresent!!.text = "A"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))
                var child = studentList[holder.adapterPosition]
                studentList[holder.adapterPosition].found = "0"
                holder.loader!!.visibility = View.VISIBLE
                val databaseReference = FirebaseDatabase.getInstance().reference
                    .child("evacuations")
                    .child(PreferenceManager.getFireRef(context))
                databaseReference.child("students")
                    .child(child.id).child("found")
                    .setValue("0").addOnSuccessListener {
                        holder.loader!!.visibility = View.GONE
                    }
            }

        }
    }

    override fun getItemCount(): Int {
//        Log.e("List Size",studentList.size.toString())
        return studentList.size
    }
}