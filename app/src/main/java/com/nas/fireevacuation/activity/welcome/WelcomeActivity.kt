package com.nas.fireevacuation.activity.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.create_account.CreateAccountActivity
import com.nas.fireevacuation.activity.sign_in.SignInActivity

class WelcomeActivity : AppCompatActivity() {
    lateinit var signIn: TextView
    lateinit var getStarted: TextView
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        context = this
        signIn = findViewById(R.id.signIn)
        getStarted = findViewById(R.id.getStarted)
        signIn.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }
        getStarted.setOnClickListener {
            val intent = Intent(context, CreateAccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }
    }
}