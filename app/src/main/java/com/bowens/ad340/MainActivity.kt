package com.bowens.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()

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
                forecastRepository.loadForecast(zipCode)
            }
        }

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        

        val weeklyForecastObserver = Observer<List<DailyForecast>>{forecastItems ->
            //update the list adapter.
            Toast.makeText(this, "Loaded Items", Toast.LENGTH_SHORT).show()
        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
    }
}
