package com.nas.fireevacuation.activity.sign_in

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.welcome.WelcomeActivity

class SignInActivity : AppCompatActivity() {
    lateinit var backButton: ImageView
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        context = this
        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }
    }
}