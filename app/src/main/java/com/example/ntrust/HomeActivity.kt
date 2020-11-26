package com.example.ntrust

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var btnContinue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnContinue = findViewById(R.id.btn_continue)
        setupEventHandlers()
    }

    private fun setupEventHandlers() {
        btnContinue.setOnClickListener{
            val intent = Intent(this, PersonalDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}