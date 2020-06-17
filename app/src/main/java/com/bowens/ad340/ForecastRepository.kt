package com.bowens.ad340

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bowens.ad340.api.CurrentWeather
import com.bowens.ad340.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.random.Random

class ForecastRepository {

    private val _currentForecast = MutableLiveData<DailyForecast>()
    val currentForecast: LiveData<DailyForecast> = _currentForecast

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast


    fun loadWeeklyForecast(zipcode: String) {
        val randomValues = List(7){ Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map { temp ->
            DailyForecast(Date(), temp, getTempDescription(temp))
        }
        _weeklyForecast.value = forecastItems
    }

    fun loadCurrentForecast(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", "apiKey")
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository :: class.java.simpleName, "error loading current weather", t)
            }

            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    _currentForecast.value = weatherResponse
                }

            }

        })
        //https://youtu.be/JGEsLpC6IU4?t=4391
    }

    private fun getTempDescription(temp: Float) : String{
        return  when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f)-> "Brr it is cold"
            in 32f.rangeTo(55f)-> "Better wear a jacket!"
            in 55f.rangeTo(65f)-> "This is more like it!"
            in 65f.rangeTo(80f)-> "That's a little warm"
            in 80f.rangeTo(90f)-> "Global Warming amirite?"
            in 90f.rangeTo(100f)-> "Where is the pool?!"
            in 100f.rangeTo(Float.MAX_VALUE)-> "Hell hath cometh..."

            else -> "Does not compute."

        }
    }
}