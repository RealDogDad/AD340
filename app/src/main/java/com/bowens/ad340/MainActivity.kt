package com.bowens.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var zipcodeEditText: EditText = findViewById(R.id.zip)
        var submitButton: Button = findViewById(R.id.submit)

        submitButton.setOnClickListener {
            val zipCode: String = zipcodeEditText.text.toString()
            if(zipCode.length != 5) {
                Toast.makeText(this, "Please enter valid Zip Code", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, zipCode, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
