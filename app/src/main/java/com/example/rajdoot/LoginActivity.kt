package com.example.rajdoot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shashank.sony.fancytoastlib.FancyToast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // for otp box traverse start
        val otp1 = findViewById<EditText>(R.id.otpbox1)
        val otp2 = findViewById<EditText>(R.id.otpbox2)
        val otp3 = findViewById<EditText>(R.id.otpbox3)
        val otp4 = findViewById<EditText>(R.id.otpbox4)

        val otpBoxes = listOf(otp1, otp2, otp3, otp4)

        for (i in otpBoxes.indices) {
            otpBoxes[i].addTextChangedListener(object : android.text.TextWatcher {
                override fun afterTextChanged(s: android.text.Editable?) {
                    if (s?.length == 1 && i < otpBoxes.lastIndex) otpBoxes[i + 1].requestFocus()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            otpBoxes[i].setOnKeyListener { _, keyCode, event ->
                if (keyCode == android.view.KeyEvent.KEYCODE_DEL && event.action == android.view.KeyEvent.ACTION_DOWN) {
                    if (otpBoxes[i].text.isEmpty() && i > 0) otpBoxes[i - 1].requestFocus()
                }
                false
            }
        }
        // otp box travers end

        // redirect to sign up page
        val signupBtn = findViewById<Button>(R.id.Loginsignupbtn)
        signupBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }



        // redirect to home page
        val loginBtn = findViewById<Button>(R.id.Loginbtn)
        loginBtn.setOnClickListener {
            // take data from sign up page
            val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
            val orgMobileNo = sharedPref.getString("mobileNo", "") ?: ""
            val mobileNo = findViewById<EditText>(R.id.et_mobile).text.toString().trim()
            val otp = otp1.text.toString() + otp2.text.toString() + otp3.text.toString() + otp4.text.toString()
            when {
                mobileNo!=orgMobileNo-> Toast.makeText(this,"Create an account first!", Toast.LENGTH_SHORT).show()
                otp!="2301" -> Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                else -> {
                    val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("login", "true")
                    editor.apply()
                    FancyToast.makeText(this,"Login successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show()
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }
}