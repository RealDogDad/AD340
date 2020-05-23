package com.bowens.ad340

interface AppNavigator {
    fun navigateToCurrentForecast(zipCode: String)

    fun navigateToLocationEntry()
}