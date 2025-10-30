package com.example.rajdoot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val profileBtn = findViewById<ImageButton>(R.id.homebtn)
        profileBtn.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
        val nameProfPage = findViewById<TextView>(R.id.nameprofile)
        val mobileProfPage = findViewById<TextView>(R.id.mobileprofile)
        val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
        val firstName = sharedPref.getString("firstName", "") ?: ""
        val lastName = sharedPref.getString("lastName", "") ?: ""
        val mobileNo = sharedPref.getString("mobileNo", "") ?: ""

        nameProfPage.text = " ${firstName} ${lastName}"
        mobileProfPage.text = " +91 ${mobileNo}"

        val logoutBtn = findViewById<Button>(R.id.logoutbtn)
        logoutBtn.setOnClickListener {
            val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("login", "false")
            editor.apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}