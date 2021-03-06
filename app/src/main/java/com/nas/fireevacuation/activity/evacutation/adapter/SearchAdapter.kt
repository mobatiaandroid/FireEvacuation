package com.nas.fireevacuation.activity.evacutation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.activity.evacutation.model.post.Post
import com.nas.fireevacuation.common.constants.PreferenceManager

class SearchAdapter(var context: Context, var studentList: ArrayList<EvacuationStudentModel>): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var studentImage: ImageView? = null
        var studentName: TextView? = null
        var registrationID: TextView? = null
        var section: TextView? = null
        var switch: Switch? = null

        init {
            studentImage = itemView.findViewById<View>(R.id.studentImage) as ImageView?
            studentName = itemView.findViewById<View>(R.id.studentName) as TextView?
            registrationID = itemView.findViewById<View>(R.id.studentID) as TextView?
            section = itemView.findViewById<View>(R.id.section) as TextView?
            switch = itemView.findViewById<View>(R.id.switch1) as Switch
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(studentList[position].photo).into(holder.studentImage!!)
        holder.studentName!!.text = studentList[position].student_name
        holder.registrationID!!.text = studentList[position].registration_id
        holder.section!!.text = studentList[position].section
        holder.switch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                var child = studentList[holder.adapterPosition]
                val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
                databaseReference.child(PreferenceManager.getFireRef(context))
                    .child("students")
                    .child(child.id).child("found")
                    .setValue("1")

            } else {
                var child = studentList[holder.adapterPosition]
                val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
                databaseReference.child(PreferenceManager.getFireRef(context))
                    .child("students")
                    .child(child.id).child("found")
                    .setValue("0")
            }

        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}