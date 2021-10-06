package com.nas.fireevacuation.activity.gallery.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
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
        holder.image!!.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.photo_view)
            var photo = dialog.findViewById<View>(R.id.photo) as ImageView
            var close = dialog.findViewById<View>(R.id.close) as ImageView
            Glide.with(context).load(photosList[position].url).into(photo)
            close.setOnClickListener {
                dialog.hide()
            }
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return photosList.size    }
}
