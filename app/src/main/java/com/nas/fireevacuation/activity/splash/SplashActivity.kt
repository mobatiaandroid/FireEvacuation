package com.nas.fireevacuation.activity.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.session_select.SessionSelectActivity
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager

class SplashActivity : AppCompatActivity() {
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this
        CommonMethods.getAccessTokenAPICall(context)
        Handler().postDelayed({
            if (PreferenceManager.getStaffID(context).equals("")) {
                val intent: Intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent: Intent = Intent(this, SessionSelectActivity::class.java)
                startActivity(intent)
                finish()
            }
        },2000)
    }
}