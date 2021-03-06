package com.nas.fireevacuation.activity.sign_in

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings.Secure.ANDROID_ID
import android.provider.Settings.Secure.getString
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.create_account.CreateAccountActivity
import com.nas.fireevacuation.activity.session_select.SessionSelectActivity
import com.nas.fireevacuation.activity.sign_in.model.signin_model.SignInModel
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import okhttp3.ResponseBody
import org.json.JSONObject
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
    lateinit var staffName: String
    lateinit var staffID: String
    lateinit var recoverAccount: TextView

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
        signIn.isEnabled = false
        val editTexts = listOf(emailID,password)
        for (editText in editTexts) {
            editText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    var et1 = password.text.toString().trim()
                    var et2 = emailID.text.toString().trim()
                    signIn.isEnabled = et1.isNotEmpty()
                            && et2.isNotEmpty()
                    if (signIn.isEnabled) {
                        signIn.setBackgroundResource(R.drawable.rounded_sign_in)
                    } else {
                        signIn.setBackgroundResource(R.drawable.create_account_disabled)
                    }

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
        }
        showHide = findViewById(R.id.showHide)
        progressBarDialog = ProgressBarDialog(context)
        recoverAccount = findViewById(R.id.recoverAccount)
        recoverAccount.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.recover_account_popup)
            val emailID = dialog.findViewById<View>(R.id.emailID) as EditText
            val submit = dialog.findViewById<View>(R.id.submit)
            submit.setOnClickListener {
                if (emailID.text.toString().equals("")) {
                    CommonMethods.showLoginErrorPopUp(context, "Field cannot be empty.")
                } else {
                    val emailPattern = CommonMethods.isEmailValid(emailID.text.toString())
                    if (!emailPattern) {
                        CommonMethods.showLoginErrorPopUp(context, "Enter a Valid Email.")
                    } else {
                        callAccountRecoveryAPI(emailID.text.toString())
                    }
            }
            }
            dialog.show()
        }
        backButton.setOnClickListener {
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
//        signIn.setBackgroundResource(R.drawable.rounded_sign_in)
        signIn.setOnClickListener {
            if (emailID.text.toString().equals("")) {
                CommonMethods.showLoginErrorPopUp(context, "Field cannot be empty.")
            } else {
                val emailPattern = CommonMethods.isEmailValid(emailID.text.toString())
                if (!emailPattern) {
                    CommonMethods.showLoginErrorPopUp(context, "Enter a Valid Email.")
                } else {
                    if (password.text.toString().equals("")) {
                        CommonMethods.showLoginErrorPopUp(context, "Field cannot be empty.")
                    } else {

                        if (CommonMethods.isInternetAvailable(context)) {
//                            signIn.setBackgroundResource(R.drawable.rounded_sign_in)
                            callLoginApi(emailID.text.toString(),password.text.toString())
                        } else {
                            CommonMethods.showLoginErrorPopUp(
                                context,
                                "Network error occurred. Please check your internet connection and try again later."
                            )
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
        showHide.setOnClickListener {
            if (passwordShowHide) {
                passwordShowHide = false
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                passwordShowHide = true
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }

        }
    }

    private fun callAccountRecoveryAPI(email: String) {
        if (CommonMethods.isInternetAvailable(context)) {
            val call: Call<ResponseBody> = ApiClient.getClient.forgotPassword(
                PreferenceManager.getAccessToken(context),
                email
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
                        if (responseCode.equals("100")) {
                            Toast.makeText(context, "Code Sent", Toast.LENGTH_SHORT).show()
                        } else {
                            CommonMethods.showLoginErrorPopUp(context, "Some Error Occured")
                        }
                    } else {
                        CommonMethods.showLoginErrorPopUp(context, "Some Error Occured")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressBarDialog!!.hide()
                    CommonMethods.showLoginErrorPopUp(context, "Some Error Occured")
                }
            })
        }else{
            CommonMethods.showLoginErrorPopUp(context,"Check your Internet connection")
        }
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
//                    Log.e("Sign In Response", response.body().toString())
                    if (signInResponse.responsecode.equals("100")) {
                        if (signInResponse.message.equals("success")) {
                            CommonMethods.showLoginErrorPopUp(context, "Login Successful")
//                            showSelectSessionPopUp()
                            staffID = signInResponse.data.user_details.staff_id
                            staffName = signInResponse.data.user_details.name
                            PreferenceManager.setStaffID(context, staffID)
                            PreferenceManager.setStaffName(context, staffName)
                            val intent = Intent(context, SessionSelectActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0,0)
                            finish()
                        }
                    } else if (signInResponse.responsecode.equals("110")) {
                        CommonMethods.showLoginErrorPopUp(context, "Incorrect username or password")
                    }  else {
                        CommonMethods.showLoginErrorPopUp(context, "Some Error Occurred")
                        CommonMethods.getAccessTokenAPICall(context)
                    }
                }
            }

            override fun onFailure(call: Call<SignInModel>, t: Throwable) {
                progressBarDialog!!.hide()
                CommonMethods.showLoginErrorPopUp(context, "Some Error Occurred")
                CommonMethods.getAccessTokenAPICall(context)
            }


        })

    }


}