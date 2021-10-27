package com.nas.fireevacuation.activity.evacutation.adapter

import com.nas.fireevacuation.R

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

import com.firebase.ui.database.FirebaseRecyclerOptions

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.common.constants.PreferenceManager


class FirebaseAdapter(
    @NonNull options: FirebaseRecyclerOptions<EvacuationStudentModel>
) :
    FirebaseRecyclerAdapter<EvacuationStudentModel, FirebaseAdapter.ViewHolder>(options) {
    override fun onBindViewHolder(
        @NonNull holder: ViewHolder,
        position: Int, @NonNull model: EvacuationStudentModel
    ) {
        Glide.with(holder.studentImage!!.context).load(model.photo).into(holder.studentImage!!)
        holder.studentName!!.text = model.student_name
        holder.registrationID!!.text = model.registration_id
        if (model.found == "1") {
            holder.absentOrPresent!!.text = "P"
            holder.switch!!.isChecked = true
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(holder.absentOrPresent!!.context, R.color.green))
        } else {
            holder.absentOrPresent!!.text = "A"
            holder.switch!!.isChecked = false
            holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(holder.absentOrPresent!!.context, R.color.pink))
        }
        holder.switch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.absentOrPresent!!.text = "P"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(holder.absentOrPresent!!.context,R.color.green))
                var child = model
                model.found = "1"
                holder.loader!!.visibility = View.VISIBLE
                val databaseReference = FirebaseDatabase.getInstance().reference
                    .child("evacuations")
                    .child(PreferenceManager.getFireRef(holder.studentName!!.context))
                databaseReference.child("students")
                    .child(child.id).child("found")
                    .setValue("1").addOnSuccessListener {
                        holder.loader!!.visibility = View.GONE

                    }

            } else {
                holder.absentOrPresent!!.text = "A"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(holder.absentOrPresent!!.context,R.color.pink))
                var child = model
                model.found = "0"
                holder.loader!!.visibility = View.VISIBLE
                val databaseReference = FirebaseDatabase.getInstance().reference
                    .child("evacuations")
                    .child(PreferenceManager.getFireRef(holder.studentName!!.context))
                databaseReference.child("students")
                    .child(child.id).child("found")
                    .setValue("0").addOnSuccessListener {
                        holder.loader!!.visibility = View.GONE
                    }
            }

        }
    }

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_evacuation_adapter, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
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
}