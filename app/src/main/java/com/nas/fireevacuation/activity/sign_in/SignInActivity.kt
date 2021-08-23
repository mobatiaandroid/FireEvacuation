package com.nas.fireevacuation.activity.sign_in

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Secure.*

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.create_account.CreateAccountActivity
import com.nas.fireevacuation.activity.session_select.SessionSelectActivity
import com.nas.fireevacuation.activity.sign_in.model.sign_in_model.SignInModel
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.Lists
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.YearGroups
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    lateinit var backButton: ImageView
    lateinit var emailID: EditText
    lateinit var password: EditText
    lateinit var context: Context
    lateinit var signIn: TextView
    lateinit var createAccount: TextView
    lateinit var showHide: TextView
    var progressBarDialog: ProgressBarDialog? = null
    var passwordShowHide:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        context = this
        backButton = findViewById(R.id.back_button)
        emailID = findViewById(R.id.emailID)
        password = findViewById(R.id.password)
        signIn = findViewById(R.id.signIn)
        createAccount = findViewById(R.id.createAccount)
        showHide = findViewById(R.id.showHide)
        progressBarDialog = ProgressBarDialog(context)
        backButton.setOnClickListener {
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        signIn.setOnClickListener {
            if (emailID.text.toString().equals("")) {
                CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be empty.")
            } else {
                var emailPattern = CommonMethods.isEmailValid(emailID.text.toString())
                if (!emailPattern) {
                    CommonMethods.showLoginErrorPopUp(context,"Alert","Enter a Valid Email.")
                } else {
                    if (password.text.toString().equals("")) {
                        CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be empty.")
                    } else {

                        if (CommonMethods.isInternetAvailable(context)) {
                            callLoginApi(emailID.text.toString(),password.text.toString())
                        } else {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Network error occurred. Please check your internet connection and try again later.")
                        }

                    }
                }
            }
        }
        createAccount.setOnClickListener {
            Toast.makeText(context,"Pressed",Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CreateAccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        showHide.setOnClickListener(View.OnClickListener {
            if (passwordShowHide) {
                passwordShowHide = false
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                passwordShowHide = true
                password.transformationMethod = HideReturnsTransformationMethod.getInstance();
            }

        })
    }

    override fun onBackPressed() {
        val intent = Intent(context, WelcomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }

    @SuppressLint("HardwareIds")
    private fun callLoginApi(email: String, pswd: String) {
        val androidID = getString(
            this.contentResolver,
            ANDROID_ID
        )
        var signInResponse: SignInModel
        val call: Call<SignInModel> = ApiClient.getClient.loginAPICall(
            PreferenceManager.getAccessToken(context),
            email,
            pswd,
            androidID,
            "1"
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<SignInModel> {
            override fun onResponse(call: Call<SignInModel>, response: Response<SignInModel>) {
                progressBarDialog!!.hide()
                if(!response.body()!!.equals("")) {
                    signInResponse = response.body()!!
                    Log.e("Sign In Response", response.body().toString())
                    if (signInResponse.responsecode.equals("200")) {
                        if (signInResponse.response.statuscode.equals("303")) {
                            CommonMethods.showLoginErrorPopUp(context, "Alert", "Login Successful")
//                            showSelectSessionPopUp()
                            val intent = Intent(context, SessionSelectActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0,0)
                            finish()
                        } else if (signInResponse.response.statuscode.equals("306")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Incorrect username")
                        } else if (signInResponse.response.statuscode.equals("305")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Incorrect Password")
                        }
                    } else {
                        CommonMethods.showLoginErrorPopUp(context,"Alert","Some Error Occurred")
                        CommonMethods.getAccessTokenAPICall(context)
                    }
                }
            }

            override fun onFailure(call: Call<SignInModel>, t: Throwable) {
                progressBarDialog!!.hide()
                CommonMethods.showLoginErrorPopUp(context,"Alert","Some Error Occurred")
                CommonMethods.getAccessTokenAPICall(context)
            }


        })

    }


}