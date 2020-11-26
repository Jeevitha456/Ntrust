package com.example.ntrust

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var edtmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var hideshow: ImageView
    private lateinit var alertText: TextView
    private lateinit var btnLogin: Button
    private var ishide: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtmail = findViewById(R.id.edt_gmail)
        edtpassword = findViewById(R.id.edt_password)
        hideshow = findViewById(R.id.img_eye)
        alertText = findViewById(R.id.alertTitle)
        btnLogin = findViewById(R.id.btn_login)
        seteventhandler()
    }

    private fun seteventhandler() {
        btnLogin.setOnClickListener {
            if (isValid()) {
                showDialog()
            }
        }
        hideshow.setOnClickListener {
            if (!ishide) {
                edtpassword.transformationMethod = PasswordTransformationMethod()
                ishide = true
            } else {
                edtpassword.transformationMethod = null
                ishide = false
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun isValid(): Boolean {
        val email: String = edtmail.text.toString().trim()
        val password: String = edtpassword.text.toString().trim()
        if (email.isEmpty() && edtpassword.text.isNullOrEmpty()) {
            alertText.setText(R.string.AlertEmailPassword)
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            return false
        }
        if (email.isEmpty()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertEmail)
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertEmailInvalid)
            return false
        }
        if (password.isEmpty()) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertPassword)
            return false
        }
        if (password.length < 5) {
            edtmail.setBackgroundResource(R.drawable.custom_edittextalert)
            edtpassword.setBackgroundResource(R.drawable.custom_edittextalert)
            alertText.setText(R.string.AlertPasswordInvalid)
            return false
        }
        edtmail.setBackgroundResource(R.drawable.customedittext)
        edtpassword.setBackgroundResource(R.drawable.customedittext)
        alertText.text = ""
        return true
    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.verify_email_dialogbox, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()
        val resendBtn = mDialogView.findViewById(R.id.btn_resend) as Button

        resendBtn.setOnClickListener {
            mAlertDialog.dismiss()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}



