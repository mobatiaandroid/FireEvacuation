package com.nas.fireevacuation.activity.evacutation.adapter

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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model.EvacuationStudentModel
import com.nas.fireevacuation.activity.evacutation.model.post.Post
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager

class StudentEvacuationAdapter(var context: Context, var studentList: ArrayList<EvacuationStudentModel>): RecyclerView.Adapter<StudentEvacuationAdapter.MyViewHolder>() {
    var absentList: ArrayList<EvacuationStudentModel> = PreferenceManager.getNotFoundList(context)
    var presentList: ArrayList<EvacuationStudentModel> = PreferenceManager.getFoundList(context)
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
            .inflate(R.layout.student_evacuation_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(studentList[position].photo).into(holder.studentImage!!)
        holder.studentName!!.text = studentList[position].student_name
        holder.studentID!!.text = studentList[position].id
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
                studentList[position].found = "1"
                absentList.remove(studentList[position])
                presentList.add(studentList[position])
                PreferenceManager.setNotFoundList(context, absentList)
                PreferenceManager.setFoundList(context, presentList)
                val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
                databaseReference.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        databaseReference.child("-MjD4ebOEfRmJRFXmZNF").addValueEventListener(object:
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (snapshot in snapshot.children){
                                    if (snapshot.child("class_id").value  != null) {
                                        Log.e("List ITem", studentList[holder.adapterPosition].id).toString()
                                        if ((snapshot.child("id").value)!!.equals(studentList[holder.adapterPosition].id)){
                                            val studentItem = Post(
                                                "1",
                                                snapshot.child("found").value.toString(),
                                                snapshot.child("id").value.toString(),
                                                snapshot.child("photo").value.toString(),
                                                snapshot.child("present").value.toString(),
                                                snapshot.child("registration_id").value.toString(),
                                                snapshot.child("assembly_point").value.toString(),
                                                snapshot.child("assembly_point_id").value.toString(),
                                                snapshot.child("class_id").value.toString(),
                                                snapshot.child("class_name").value.toString(),
                                                snapshot.child("created_at").value.toString(),
                                                snapshot.child("section").value.toString(),
                                                snapshot.child("staff_id").value.toString(),
                                                snapshot.child("student_name").value.toString(),
                                                snapshot.child("subject").value.toString(),
                                                snapshot.child("updated_at").value.toString())
                                            val postValues: Map<String, Any> = studentItem.toMap() as Map<String, Any>
                                            databaseReference.child("-MjD4ebOEfRmJRFXmZNF").child(snapshot.child("id").value.toString()).updateChildren(postValues)
                                                .addOnSuccessListener { Log.e("Success","Success") }
                                            break

                                        }
                                    } else {
                                        break
                                    }
                                }

                            }
                            override fun onCancelled(error: DatabaseError) {}
                        })
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
//                CommonMethods.markAttendanceFound(studentList[holder.adapterPosition].id)

//
            } else {
                holder.absentOrPresent!!.text = "A"
                holder.absentOrPresent!!.setBackgroundColor(ContextCompat.getColor(context,R.color.pink))
                studentList[position].found = "1"
                absentList.add(studentList[position])
                presentList.remove(studentList[position])
                PreferenceManager.setNotFoundList(context, absentList)
                PreferenceManager.setFoundList(context, presentList)
                val databaseReference = FirebaseDatabase.getInstance().reference.child("evacuations")
                databaseReference.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        databaseReference.child("-MjD4ebOEfRmJRFXmZNF").addValueEventListener(object:
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (snapshot in snapshot.children){
                                    if (snapshot.child("class_id").value  != null) {
                                        if ((snapshot.child("id").value)!!.equals(studentList[holder.adapterPosition].id)){
                                            val studentItem = Post(
                                                "0",
                                                snapshot.child("found").value.toString(),
                                                snapshot.child("id").value.toString(),
                                                snapshot.child("photo").value.toString(),
                                                snapshot.child("present").value.toString(),
                                                snapshot.child("registration_id").value.toString(),
                                                snapshot.child("assembly_point").value.toString(),
                                                snapshot.child("assembly_point_id").value.toString(),
                                                snapshot.child("class_id").value.toString(),
                                                snapshot.child("class_name").value.toString(),
                                                snapshot.child("created_at").value.toString(),
                                                snapshot.child("section").value.toString(),
                                                snapshot.child("staff_id").value.toString(),
                                                snapshot.child("student_name").value.toString(),
                                                snapshot.child("subject").value.toString(),
                                                snapshot.child("updated_at").value.toString())
                                            val postValues: Map<String, Any> = studentItem.toMap() as Map<String, Any>
                                            databaseReference.child("-MjD4ebOEfRmJRFXmZNF").child(snapshot.child("id").value.toString()).updateChildren(postValues)
                                                .addOnSuccessListener { Log.e("Success","Success") }
                                            break

                                        }
                                    } else {
                                        break
                                    }
                                }

                            }
                            override fun onCancelled(error: DatabaseError) {}
                        })
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
//                CommonMethods.markAttendanceNotFound(studentList[holder.adapterPosition].id)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.e("List Size",studentList.size.toString())
        return studentList.size
    }
}