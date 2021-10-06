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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.webkit.WebSettings

import android.webkit.WebView

import android.webkit.WebViewClient
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
        Glide.with(context).load(videosList[position].thumbnail).into(holder.image!!)
        holder.image!!.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.video_view)
            val dataUrl = "<html>" +
                    "<body>" +
                    "<br>" +
                    "<iframe width=\"350\" height=\"350\" src=\"" + videosList[position].url + "\" frameborder=\"0\" allowfullscreen/>" +
                    "</body>" +
                    "</html>"
            val displayYoutubeVideo = dialog.findViewById<View>(R.id.mWebView) as WebView
            displayYoutubeVideo.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }
            }
            val webSettings = displayYoutubeVideo.settings
            webSettings.javaScriptEnabled = true
            displayYoutubeVideo.loadData(dataUrl, "text/html", "utf-8")
//            Glide.with(context).load(videosList[position].url).into(photo)
//            close.setOnClickListener {
//                dialog.hide()
//            }
            dialog.show()
        }}

    override fun getItemCount(): Int {
        return videosList.size
    }
}