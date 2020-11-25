package com.example.ntrust

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var edtmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var hide_show: ImageView
    private lateinit var alertText: TextView
    private lateinit var btnLogin: Button
    var ishide: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtmail = findViewById(R.id.edt_gmail) as EditText
        edtpassword = findViewById(R.id.edt_password) as EditText
        hide_show = findViewById(R.id.img_eye) as ImageView
        alertText = findViewById(R.id.alertTitle) as TextView
        btnLogin = findViewById(R.id.btn_login) as Button
        seteventhandler()
    }

    private fun seteventhandler() {
        btnLogin.setOnClickListener {
            if (isValid()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
        hide_show.setOnClickListener() {
            if (!ishide) {
                edtpassword.setTransformationMethod(PasswordTransformationMethod())
                ishide = true
            } else {
                edtpassword.setTransformationMethod(null)
                ishide = false
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun isValid(): Boolean {
        val email: String = edtmail.text.toString().trim()
        val password: String = edtpassword.text.toString().trim()
        if (email.isEmpty() && edtpassword.text.isNullOrEmpty()) {
            alertText.setText("Please enter email id and password")
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            return false
        }
        if (email.isEmpty()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText("Please enter email id")
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText("Entered Email is invalid")
            return false
        }
        if (password.isEmpty()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText("Please enter password")
            return false
        }
        if (password.length < 5) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText("Please enter minimum of 4 character password")
            return false
        }
        edtmail.setBackgroundResource(R.drawable.customedittext)
        edtpassword.setBackgroundResource(R.drawable.customedittext)
        alertText.text = ""
        return true
    }
}