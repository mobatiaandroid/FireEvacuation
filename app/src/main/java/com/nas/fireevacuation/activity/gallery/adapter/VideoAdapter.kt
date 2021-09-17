package com.nas.fireevacuation.activity.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.fireevacuation.R

class VideoAdapter(var context: Context, var videosList: ArrayList<com.nas.fireevacuation.activity.gallery.model.videos_model.Lists>)
    : RecyclerView.Adapter<VideoAdapter.MyViewHolder>() {
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
        Glide.with(context).load(videosList[position].thumbnail).into(holder.image!!)    }

    override fun getItemCount(): Int {
        return videosList.size
    }
}
