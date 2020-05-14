package com.bowens.ad340.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bowens.ad340.R

class ForecastDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)


        setTitle(R.string.forecast_details)
    }
}
