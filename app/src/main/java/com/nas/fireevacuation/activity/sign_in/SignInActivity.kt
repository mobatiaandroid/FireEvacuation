package com.nas.fireevacuation.activity.sign_in

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Secure.*

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.create_account.CreateAccountActivity
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext

class SignInActivity : AppCompatActivity() {
    lateinit var backButton: ImageView
    lateinit var emailID: EditText
    lateinit var password: EditText
    lateinit var context: Context
    lateinit var signIn: TextView
    lateinit var createAccount: TextView
    lateinit var showHide: TextView
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
        val call: Call<ResponseBody> = ApiClient.getClient.loginAPICall(
            PreferenceManager.getAccessToken(context),
            email,
            pswd,
            androidID,
            "1"
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseData = response.body()
                if (responseData != null) {
                    val jsonObject = JSONObject(responseData.string())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}