package com.bowens.ad340

import android.accounts.AuthenticatorDescription
import java.util.*

data class DailyForecast(
    val date: Date,
    val temp: Float,
    val description: String

)