package com.nas.fireevacuation.activity.my_profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.gallery.GalleryActivity
import com.nas.fireevacuation.activity.my_profile.model.LogoutModel
import com.nas.fireevacuation.activity.sign_in.SignInActivity
import com.nas.fireevacuation.activity.staff_attendance.AllStudentsActivity
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

import android.content.DialogInterface




class MyProfileActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var homeButton: ImageView
    lateinit var attendanceButton: ImageView
    lateinit var gallery: ImageView
    lateinit var notifications: TextView
    lateinit var checkout: TextView
    lateinit var settings: TextView
    lateinit var changePassword: TextView
    lateinit var editButton: ImageView
    lateinit var staffName: EditText
    lateinit var staffDesignation: EditText
    var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        context = this
        homeButton = findViewById(R.id.home)
        attendanceButton = findViewById(R.id.attendence)
        gallery = findViewById(R.id.gallery)
        notifications = findViewById(R.id.notifications)
        settings = findViewById(R.id.settings)
        checkout = findViewById(R.id.checkout)
        editButton = findViewById(R.id.edit)
        staffName = findViewById(R.id.staffName)
        staffDesignation = findViewById(R.id.staffDesignation)
        changePassword = findViewById(R.id.changePassword)
        progressBarDialog = ProgressBarDialog(context)
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
        gallery.setOnClickListener {
            val intent = Intent(context, GalleryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        changePassword.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.change_password_popup)

            val currentPassword = dialog.findViewById<View>(R.id.currentPassword) as EditText
            val newPassword = dialog.findViewById<View>(R.id.newPassword) as EditText
            val confirmPassword = dialog.findViewById<View>(R.id.confirmPassword) as EditText
            val submit = dialog.findViewById<View>(R.id.submit)
            submit.isEnabled = false
            val editTexts = listOf(currentPassword, newPassword, confirmPassword)
            for (editText in editTexts) {
                editText.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        var et1 = currentPassword.text.toString().trim()
                        var et2 = newPassword.text.toString().trim()
                        var et3 = confirmPassword.text.toString().trim()

                        submit.isEnabled = et1.isNotEmpty()
                                && et2.isNotEmpty()
                                && et3.isNotEmpty()
                        if (submit.isEnabled) {
                            submit.setBackgroundResource(R.drawable.rounded_sign_in)
                        } else {
                            submit.setBackgroundResource(R.drawable.create_account_disabled)
                        }
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int, count: Int, after: Int) {
                    }

                    override fun afterTextChanged(
                        s: Editable
                    ) {
                    }
                })
            }
            submit.setOnClickListener {
                if (currentPassword.text.toString().equals("") || newPassword.text.toString().equals("") || confirmPassword.text.toString().equals("")) {
                    CommonMethods.showLoginErrorPopUp(context, "Field cannot be empty.")
                } else if (!newPassword.text.toString().equals(confirmPassword.text.toString())){
                    CommonMethods.showLoginErrorPopUp(context, "Passwords do not match")
                } else {
                    changePasswordAPICall(currentPassword.text.toString(),newPassword.text.toString())
                }
            }

            dialog.show()
        }

        homeButton.setOnClickListener {
            val intent = Intent(context, StaffHomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        attendanceButton.setOnClickListener {
            val intent = Intent(context, AllStudentsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        gallery.setOnClickListener {
            val intent = Intent(context, GalleryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        checkout.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    signOutCall()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, id -> //  Action for 'NO' Button
                    dialog.cancel()
                }
            val alert: AlertDialog = builder.create()
            alert.show();

        }
    }

    private fun changePasswordAPICall(current: String, new: String) {
        if (CommonMethods.isInternetAvailable(context)) {
            val call: Call<ResponseBody> = ApiClient.getClient.changePassword(
                PreferenceManager.getAccessToken(context), PreferenceManager.getStaffID(context),
                current, new
            )
            progressBarDialog!!.show()
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    progressBarDialog!!.hide()
                    val responseData = response.body()
                    if (responseData != null) {
                        val jsonObject = JSONObject(responseData.string())
                        val responseCode: String = jsonObject.optString("responsecode")
                        if (responseCode.equals("100")) if (responseCode.equals("100")) {
                            CommonMethods.showLoginErrorPopUp(context, "Success")
                        }else if (responseCode.equals("130")) {
                            CommonMethods.showLoginErrorPopUp(context, "Incorrect Password")
                        } else {
                            CommonMethods.showLoginErrorPopUp(context, "Some Error Occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressBarDialog!!.hide()
                }
            })
        } else {
            CommonMethods.showLoginErrorPopUp(context,"Check your Internet connection")
        }
    }

    private fun signOutCall() {
        val androidID = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var logoutResponse: LogoutModel
        if (CommonMethods.isInternetAvailable(context)) {
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
                    if (!response.body()!!.equals("")) {
                        logoutResponse = response.body()!!
                        if (logoutResponse.responsecode.equals("100")) {
                            PreferenceManager.setStaffID(context, "")
                            val intent = Intent(context, SignInActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                            finish()
                        } else if (logoutResponse.responsecode.equals("103")) {
                            CommonMethods.showLoginErrorPopUp(context, "Invalid Request")
                        } else if (logoutResponse.responsecode.equals("400")) {
                            CommonMethods.showLoginErrorPopUp(context, "Access Token Missing")
                        } else if (logoutResponse.responsecode.equals("402")) {
                            CommonMethods.showLoginErrorPopUp(context, "Invalid Access Token")
                        } else {
                            CommonMethods.showLoginErrorPopUp(context, "Some Error occured")
                        }
                    }
                }

                override fun onFailure(call: Call<LogoutModel>, t: Throwable) {
                    CommonMethods.showLoginErrorPopUp(context, "Some Error occured")
                }

            })
        } else{
            CommonMethods.showLoginErrorPopUp(context,"Check your Internet connection")
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(context, AllStudentsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }
}