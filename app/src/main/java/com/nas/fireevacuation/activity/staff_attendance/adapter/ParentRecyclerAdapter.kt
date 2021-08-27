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
            alphabet = (itemView.findViewById<View>(com.nas.fireevacuation.R.id.alphabet) as TextView?)!!
            childRecyclerView = (itemView.findViewById<View>(com.nas.fireevacuation.R.id.childRecyclerView) as RecyclerView?)!!
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
//class ParentRecyclerViewAdapter(exampleList: ArrayList<ParentModel>, context: Context) :
//    RecyclerView.Adapter<MyViewHolder>() {
//    private val parentModelArrayList: ArrayList<ParentModel>
//    var cxt: Context
//
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var category: TextView
//        var childRecyclerView: RecyclerView
//
//        init {
//            category = itemView.findViewById(R.id.Movie_category)
//            childRecyclerView = itemView.findViewById(R.id.Child_RV)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view: View = LayoutInflater.from(parent.context)
//            .inflate(R.layout.parent_recyclerview_items, parent, false)
//        return MyViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return parentModelArrayList.size()
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentItem: ParentModel = parentModelArrayList[position]
//        val layoutManager: RecyclerView.LayoutManager =
//            LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false)
//        holder.childRecyclerView.layoutManager = layoutManager
//        holder.childRecyclerView.setHasFixedSize(true)
//        holder.category.setText(currentItem.movieCategory())
//        val arrayList: ArrayList<ChildModel> = ArrayList()
//
//        // added the first child row
//        if (parentModelArrayList[position].movieCategory().equals("Category1")) {
//            arrayList.add(ChildModel(R.drawable.themartian, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moana, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.mov2, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.blackp, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi2, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood1, "Movie Name"))
//        }
//
//        // added in second child row
//        if (parentModelArrayList[position].movieCategory().equals("Category2")) {
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi2, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi3, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi1, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi4, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi5, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi6, "Movie Name"))
//        }
//
//        // added in third child row
//        if (parentModelArrayList[position].movieCategory().equals("Category3")) {
//            arrayList.add(ChildModel(R.drawable.hollywood6, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood5, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood4, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood3, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood2, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood1, "Movie Name"))
//        }
//
//        // added in fourth child row
//        if (parentModelArrayList[position].movieCategory().equals("Category4")) {
//            arrayList.add(ChildModel(R.drawable.bestofoscar6, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar5, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar4, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar3, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar2, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar1, "Movie Name"))
//        }
//
//        // added in fifth child row
//        if (parentModelArrayList[position].movieCategory().equals("Category5")) {
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi4, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood2, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar4, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.mov2, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood1, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar1, "Movie Name"))
//        }
//
//        // added in sixth child row
//        if (parentModelArrayList[position].movieCategory().equals("Category6")) {
//            arrayList.add(ChildModel(R.drawable.hollywood5, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.blackp, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar4, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.moviedubbedinhindi6, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.hollywood1, "Movie Name"))
//            arrayList.add(ChildModel(R.drawable.bestofoscar6, "Movie Name"))
//        }
//        val childRecyclerViewAdapter =
//            ChildRecyclerViewAdapter(arrayList, holder.childRecyclerView.context)
//        holder.childRecyclerView.adapter = childRecyclerViewAdapter
//    }
//
//    init {
//        parentModelArrayList = exampleList
//        cxt = context
//    }
//}