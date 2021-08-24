package com.nas.fireevacuation.activity.create_account

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.create_account.model.CreateAccountModel
import com.nas.fireevacuation.activity.sign_in.SignInActivity
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var name: EditText
    lateinit var emailID: EditText
//    lateinit var password: EditText
//    lateinit var confirmPassword: EditText
    lateinit var mobileNo: EditText
//    lateinit var showHide1: TextView
//    lateinit var showHide2: TextView
    lateinit var createAccount: Button
    lateinit var backButton: ImageView
    var passwordShowHide1:Boolean=false
    var passwordShowHide2:Boolean=false
    var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        context = this
        name = findViewById(R.id.name)
        emailID = findViewById(R.id.emailID)
//        password = findViewById(R.id.password)
//        confirmPassword = findViewById(R.id.confrimPassword)
        mobileNo = findViewById(R.id.mobileNo)
//        showHide1 = findViewById(R.id.showHide1)
//        showHide2 = findViewById(R.id.showHide2)
        createAccount = findViewById(R.id.createAccount)
        backButton = findViewById(R.id.back_button)
        progressBarDialog = ProgressBarDialog(context)
//        showHide1.setOnClickListener(View.OnClickListener {
//            if (passwordShowHide1) {
//                passwordShowHide1 = false
//                password.transformationMethod = PasswordTransformationMethod.getInstance()
//            } else {
//                passwordShowHide1 = true
//                password.transformationMethod = HideReturnsTransformationMethod.getInstance();
//            }
//
//        })
//        showHide2.setOnClickListener(View.OnClickListener {
//            if (passwordShowHide2) {
//                passwordShowHide2 = false
//                confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//            } else {
//                passwordShowHide2 = true
//                confirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
//            }
//
//        })
        createAccount.setBackgroundResource(R.drawable.create_account_disabled)
        createAccount.isEnabled = false
        val editTexts = listOf(name, emailID, mobileNo)
        for (editText in editTexts) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    var et1 = name.text.toString().trim()
                    var et2 = emailID.text.toString().trim()
                    var et3 = mobileNo.text.toString().trim()

                    createAccount.isEnabled = et1.isNotEmpty()
                            && et2.isNotEmpty()
                            && et3.isNotEmpty()
                    if (createAccount.isEnabled) {
                        createAccount.setBackgroundResource(R.drawable.rounded_sign_in)
                    } else {
                        createAccount.setBackgroundResource(R.drawable.create_account_disabled)
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
        createAccount.setOnClickListener {
            if (name.text.toString().equals("") || emailID.text.toString().equals("") || mobileNo.text.toString().equals("")){
                CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be left Empty")
            } else {
                createAccount.setBackgroundResource(R.drawable.rounded_sign_in)
                var emailPattern = CommonMethods.isEmailValid(emailID.text.toString())
                if (!emailPattern) {
                    CommonMethods.showLoginErrorPopUp(context,"Alert","Enter a Valid Email.")
                } else {
                        if (CommonMethods.isInternetAvailable(context)) {
                            callCreateAccountApi()
                        } else {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Network error occurred. Please check your internet connection and try again later.")
                        }

                }
            }
        }
        backButton.setOnClickListener {
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(context, WelcomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0,0)
        finish()
    }

    @SuppressLint("HardwareIds")
    private fun callCreateAccountApi() {
        val androidID = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var createAccountResponse: CreateAccountModel
        val call: Call<CreateAccountModel> = ApiClient.getClient.signUpAPICall(
            PreferenceManager.getAccessToken(context),
            emailID.text.toString(),
            androidID,
            "1"
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<CreateAccountModel> {
            override fun onResponse(
                call: Call<CreateAccountModel>,
                response: Response<CreateAccountModel>
            ) {
                progressBarDialog!!.hide()
                if(!response.body()!!.equals("")) {
                    createAccountResponse = response.body()!!
                    if (createAccountResponse.responsecode == "121") {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Already Registered")
                            val intent = Intent(context, SignInActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0,0)
                            finish()
                    } else if (createAccountResponse.responsecode == "114") {
                        CommonMethods.showLoginErrorPopUp(context,"Alert","Invalid User")
                    }else if (createAccountResponse.responsecode == "402") {
                        CommonMethods.showLoginErrorPopUp(context,"Alert","Some Error Occurred")
                        CommonMethods.getAccessTokenAPICall(context)
                    }
                }
            }

            override fun onFailure(call: Call<CreateAccountModel>, t: Throwable) {
                progressBarDialog!!.hide()
                CommonMethods.showLoginErrorPopUp(context,"Alert","Some Error Occurred")
                CommonMethods.getAccessTokenAPICall(context)
            }

        })
    }
}