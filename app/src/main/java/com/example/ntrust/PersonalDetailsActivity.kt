package com.example.ntrust

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class PersonalDetailsActivity : AppCompatActivity() {
    private var cal = Calendar.getInstance()
    private lateinit var edtdob: EditText
    private lateinit var edtweight: Spinner
    private lateinit var edtheight: Spinner
    private lateinit var edtgender: Spinner
    private lateinit var btnback: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_details)

        edtdob = findViewById(R.id.edt_DOB)
        edtgender = findViewById(R.id.edt_gender)
        edtheight = findViewById(R.id.edtdrop_height)
        edtweight = findViewById(R.id.edtdrop_weight)
        btnback=findViewById(R.id.back)
        setupEventHandlers()
    }

    private fun setupEventHandlers() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        val genderOptions = arrayOf("Gender", "Male", "Female")
        val weightOptions = arrayOf("Weight Type")
        val heightOptions = arrayOf("Height Type")
        edtdob.setOnClickListener {
            DatePickerDialog(
                this@PersonalDetailsActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, genderOptions
        )
        edtgender.adapter = adapter
        edtgender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent?.getItemAtPosition(position).toString()
            }
        }


        val heightadapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, heightOptions
        )
        edtheight.adapter = heightadapter
        edtheight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent?.getItemAtPosition(position).toString()
            }
        }

        val weightadapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, weightOptions
        )
        edtweight.adapter = weightadapter
        edtweight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent?.getItemAtPosition(position).toString()
            }
        }

        btnback.setOnClickListener{
            finish()
        }
    }

    @SuppressLint("WeekBasedYear")
    private fun updateDateInView() {
        val myFormat = "dd-MM-YYYY"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edtdob.setText(sdf.format(cal.time))
    }

}