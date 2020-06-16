package com.bowens.ad340

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class Location {
    data class Zipcode(val Zipcode: String) : Location()
}

private const val KEY_ZIPCODE = "key_zipcode"

class LocationRepository(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _savedLocation: MutableLiveData<Location> = MutableLiveData()
    val savedLocation: LiveData<Location> = _savedLocation

    fun saveLocation(location: Location) {
        when (location) {
            is Location.Zipcode -> preferences.edit().putString(KEY_ZIPCODE, location.Zipcode).apply()
        }
    }
}