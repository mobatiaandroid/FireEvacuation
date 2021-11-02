package com.nas.fireevacuation.activity.gallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.gallery.adapter.PhotoAdapter
import com.nas.fireevacuation.activity.gallery.adapter.VideoAdapter
import com.nas.fireevacuation.activity.gallery.model.photos_model.Lists
import com.nas.fireevacuation.activity.gallery.model.photos_model.PhotosModel
import com.nas.fireevacuation.activity.gallery.model.videos_model.VideosModel
import com.nas.fireevacuation.activity.my_profile.MyProfileActivity
import com.nas.fireevacuation.activity.staff_attendance.AllStudentsActivity
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var homeButton: ImageView
    lateinit var attendanceButton: ImageView
    lateinit var myProfile: ImageView
    lateinit var photos: View
    lateinit var videos: View
    lateinit var photosText: TextView
    lateinit var videosText: TextView
    lateinit var recyclerView: RecyclerView
    var progressBarDialog: ProgressBarDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        context = this
        homeButton = findViewById(R.id.home)
        attendanceButton = findViewById(R.id.attendance)
        myProfile = findViewById(R.id.myProfile)
        photos = findViewById(R.id.photos)
        videos = findViewById(R.id.videos)
        photosText = findViewById(R.id.photosText)
        videosText = findViewById(R.id.videosText)
        progressBarDialog = ProgressBarDialog(context)
        recyclerView = findViewById(R.id.recyclerView)
        showPhotos()
        photos.setOnClickListener {
            showPhotos()
        }
        videos.setOnClickListener {
            showVideos()
        }
        myProfile.setOnClickListener {
            val intent = Intent(context, MyProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
        homeButton.setOnClickListener {
            val intent = Intent(context, StaffHomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
        attendanceButton.setOnClickListener {
            val intent = Intent(context, AllStudentsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(context, AllStudentsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }
    private fun showVideos() {
        videos.setBackgroundResource(R.drawable.curved_white_rectangle)
        photos.setBackgroundResource(0)
        videosText.setTextColor(resources.getColor(R.color.pink))
        photosText.setTextColor(resources.getColor(R.color.white))
        if (CommonMethods.isInternetAvailable(context)) {
            val call: Call<VideosModel> = ApiClient.getClient.videosAPICall(
                PreferenceManager.getAccessToken(context),
                "1"
            )
            var videoResponse: VideosModel
            var videosList: ArrayList<com.nas.fireevacuation.activity.gallery.model.videos_model.Lists> =
                ArrayList()
            var i = 0
            progressBarDialog!!.show()
            call.enqueue(object : Callback<VideosModel> {
                override fun onResponse(call: Call<VideosModel>, response: Response<VideosModel>) {
                    progressBarDialog!!.hide()
                    if (!response.body()!!.equals("")) {
                        videoResponse = response.body()!!
                        if (videoResponse.responsecode.toString().equals("100")) {
                            if (videoResponse.message.equals("success")) {

//                            Log.e("Response", videoResponse.toString())


                                while (i < videoResponse.data.lists.size) {
                                    videosList.add(videoResponse.data.lists[i])
                                    i++
                                }
//                            Log.e("Response", videosList.toString())
                                val photosAdapter = VideoAdapter(context, videosList)
                                recyclerView.hasFixedSize()
                                recyclerView.layoutManager = GridLayoutManager(context, 3)
                                recyclerView.adapter = photosAdapter
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<VideosModel>, t: Throwable) {
                    Log.e("Error", "Error")
                }

            })
        } else{
            CommonMethods.showLoginErrorPopUp(context,"Check your Internet connection")
        }
    }

    private fun showPhotos() {
        photos.setBackgroundResource(R.drawable.curved_white_rectangle)
        videos.setBackgroundResource(0)
        photosText.setTextColor(resources.getColor(R.color.pink))
        videosText.setTextColor(resources.getColor(R.color.white))
        if (CommonMethods.isInternetAvailable(context)) {
            val call: Call<PhotosModel> = ApiClient.getClient.photosAPICall(
                PreferenceManager.getAccessToken(context),
                "77",
                "1"
            )
            var photosResponse: PhotosModel
            var photosList: ArrayList<Lists> = ArrayList()
            var i = 0
            progressBarDialog!!.show()
            call.enqueue(object : Callback<PhotosModel> {
                override fun onResponse(call: Call<PhotosModel>, response: Response<PhotosModel>) {
                    progressBarDialog!!.hide()
                    if (!response.body()!!.equals("")) {
                        photosResponse = response.body()!!
//                    Log.e("Photos1",photosResponse.toString())
//                    Log.e("Response",photosResponse.responsecode.toString())
//                    Log.e("Response",photosResponse.message.toString())
                        if (photosResponse.responsecode.toString().equals("100")) {
//                        Log.e("Photo2s",photosResponse.toString())
                            if (photosResponse.message.equals("success")) {
//                            Log.e("Photos3",photosResponse.toString())
                                while (i < photosResponse.data.lists.size) {
                                    photosList.add(photosResponse.data.lists[i])
                                    i++
                                }
//                            Log.e("Photos5",photosList.toString())
                                val photosAdapter = PhotoAdapter(context, photosList)
                                recyclerView.hasFixedSize()
                                recyclerView.layoutManager = GridLayoutManager(context, 3)
                                recyclerView.adapter = photosAdapter
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<PhotosModel>, t: Throwable) {
                    Log.e("Error", "Error")
                }

            })
        } else{
            CommonMethods.showLoginErrorPopUp(context,"Check your Internet connection")
        }
    }
}