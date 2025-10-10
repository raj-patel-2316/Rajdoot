package com.example.rajdoot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textHomePage = findViewById<TextView>(R.id.texthomepage)
        val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
        val firstName = sharedPref.getString("firstName", "") ?: ""
        val lastName = sharedPref.getString("lastName", "") ?: ""

        textHomePage.text = "Hello,${firstName} ${lastName}"

        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("login", "false")
            editor.apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}