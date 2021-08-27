package com.nas.fireevacuation.common.constants

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommonMethods {
    companion object{
        fun showLoginErrorPopUp(context: Context, head: String, message: String) {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.dialog_alert)
            val text = dialog.findViewById<View>(R.id.textDialog) as TextView
            text.text = message
            dialog.show()
        }

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isInternetAvailable(context: Context): Boolean
        {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }
        fun getAccessTokenAPICall(context: Context) {
            val call: Call<ResponseBody> = ApiClient.getClient.accessToken(
                "password",
                "testclient",
                "testpass",
                "krishnaraj.s@mobatia.com",
                "admin123"
            )
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val jsonObject = JSONObject(responseData.string())
                        Log.e("Response",response.body().toString())
                        val accessToken: String = jsonObject.optString("access_token")
                        PreferenceManager.setAccessToken(context, accessToken)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    showLoginErrorPopUp(context,"Alert","Invalid Grant")
                }

            })
        }


    }
}