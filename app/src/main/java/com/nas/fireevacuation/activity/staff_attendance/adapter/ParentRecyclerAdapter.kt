package com.nas.fireevacuation.activity.staff_attendance.adapter

import android.content.Context
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists

import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_home.model.students_model.StudentModel
import android.widget.LinearLayout


class ParentRecyclerAdapter(var context: Context, var studentList: ArrayList<Lists>) : RecyclerView.Adapter<ParentRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var alphabet: TextView
        var childRecyclerView: RecyclerView
        init {
            alphabet = (itemView.findViewById<View>(R.id.alphabet) as TextView?)!!
            childRecyclerView = (itemView.findViewById<View>(R.id.childRecyclerView) as RecyclerView?)!!
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(com.nas.fireevacuation.R.layout.parent_recycler_items, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: ArrayList<Lists> = studentList
        var layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.childRecyclerView.layoutManager = layoutManager
        holder.childRecyclerView.setHasFixedSize(true)
        holder.alphabet.text = currentItem[position].name[0].toString()
        val arrayList: ArrayList<StudentModel> = ArrayList()
        val childLayoutManager = LinearLayoutManager(holder.childRecyclerView.context, LinearLayoutManager.VERTICAL, false)
        holder.childRecyclerView.apply {
            layoutManager = childLayoutManager
            adapter = ChildRecyclerAdapter(context,studentList)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

}
