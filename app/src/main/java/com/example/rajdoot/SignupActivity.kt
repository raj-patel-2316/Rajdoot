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


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
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
        // for otp box traverse end

        // redirect to login page
        val signupBtn = findViewById<Button>(R.id.loginbtn)
        signupBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // redirect to login page after sign up
        val signupBtnmain = findViewById<Button>(R.id.signupbtn)
        signupBtnmain.setOnClickListener {
            val mobileNo = findViewById<EditText>(R.id.signupet_mobile).text.toString().trim()
            val firstName = findViewById<EditText>(R.id.firstname).text.toString().trim()
            val lastName = findViewById<EditText>(R.id.lastname).text.toString().trim()
            val otp = otp1.text.toString() + otp2.text.toString() + otp3.text.toString() + otp4.text.toString()

            when {
                firstName.isEmpty() -> Toast.makeText(this,"Please enter first name", Toast.LENGTH_SHORT).show()
                lastName.isEmpty() -> Toast.makeText(this,"Please enter last name", Toast.LENGTH_SHORT).show()
                mobileNo.isEmpty() -> Toast.makeText(this,"Please enter mobile number", Toast.LENGTH_SHORT).show()
                mobileNo.length != 10 -> Toast.makeText(this, "Mobile number must be 10 digits", Toast.LENGTH_SHORT).show()
                otp!="2301" -> Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                else -> {
                    // put data in share prefernce so from other file access it
                    val sharedPref = getSharedPreferences("userData", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("firstName", firstName)
                    editor.putString("lastName", lastName)
                    editor.putString("mobileNo", mobileNo)
                    editor.putString("login","false")
                    editor.apply()

                    FancyToast.makeText(this,"Account created successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}