package com.nas.fireevacuation.activity.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.gallery.model.photos_model.Lists

class PhotoAdapter(var context: Context, var photosList: ArrayList<Lists>): RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image: ImageView? = null
        init {
            image = itemView.findViewById(R.id.roundedImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(photosList[position].url).into(holder.image!!)
    }

    override fun getItemCount(): Int {
        return photosList.size    }
}

