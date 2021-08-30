package com.nas.fireevacuation.activity.my_profile

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.evacutation.model.EvacuationModel
import com.nas.fireevacuation.activity.my_profile.model.LogoutModel
import com.nas.fireevacuation.activity.sign_in.SignInActivity
import com.nas.fireevacuation.activity.staff_attendance.StaffAttendanceActivity
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var homeButton: ImageView
    lateinit var attendanceButton: ImageView
    lateinit var notifications: TextView
    lateinit var checkout: TextView
    lateinit var settings: TextView
    lateinit var editButton: ImageView
    lateinit var staffName: EditText
    lateinit var staffDesignation: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        context = this
        homeButton = findViewById(R.id.home)
        attendanceButton = findViewById(R.id.attendence)
        notifications = findViewById(R.id.notifications)
        settings = findViewById(R.id.settings)
        checkout = findViewById(R.id.checkout)
        editButton = findViewById(R.id.edit)
        staffName = findViewById(R.id.staffName)
        staffDesignation = findViewById(R.id.staffDesignation)
        staffName.setOnKeyListener(null)
        staffDesignation.setOnKeyListener(null)
        staffName.text = Editable.Factory.getInstance().newEditable(PreferenceManager.getStaffName(context))
        var click = 1
        editButton.setOnClickListener {
            if (click == 1){
                staffName.isEnabled = true
                staffDesignation.isEnabled = true
                editButton.setImageResource(R.drawable.tick_icon)
                staffName.text.clear()
                staffName.hint = PreferenceManager.getStaffName(context)
                staffDesignation.text.clear()
                staffDesignation.hint = "English Lecturer"
                click = 2
            } else if(click == 2){
                editButton.setImageResource(R.drawable.edit_icon)
                click = 1
            }

        }

        homeButton.setOnClickListener {
            val intent = Intent(context, StaffHomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        attendanceButton.setOnClickListener {
            val intent = Intent(context, StaffAttendanceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        checkout.setOnClickListener {
            signOutCall()

        }
    }

    private fun signOutCall() {
        val androidID = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var logoutResponse: LogoutModel
        val call: Call<LogoutModel> = ApiClient.getClient.logOut(
            PreferenceManager.getAccessToken(context),
            PreferenceManager.getStaffID(context),
            androidID,
            "1"
        )
        call.enqueue(object : Callback<LogoutModel> {
            override fun onResponse(
                call: Call<LogoutModel>,
                response: Response<LogoutModel>
            ) {
                if(!response.body()!!.equals("")) {
                    logoutResponse = response.body()!!
                    if (logoutResponse.responsecode.equals("100")) {
                        val intent = Intent(context, SignInActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        finish()
                    } else if (logoutResponse.responsecode.equals("103")) {
                        CommonMethods.showLoginErrorPopUp(context, "Alert", "Invalid Request")
                    } else if (logoutResponse.responsecode.equals("400")) {
                        CommonMethods.showLoginErrorPopUp(context, "Alert", "Access Token Missing")
                    } else if (logoutResponse.responsecode.equals("402")) {
                        CommonMethods.showLoginErrorPopUp(context, "Alert", "Invalid Access Token")
                    } else {
                        CommonMethods.showLoginErrorPopUp(context, "Alert", "Some Error occured")
                    }
                }
            }

            override fun onFailure(call: Call<LogoutModel>, t: Throwable) {
                CommonMethods.showLoginErrorPopUp(context, "Alert", "Some Error occured")            }

        })
    }
}