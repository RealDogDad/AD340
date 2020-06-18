package com.bowens.ad340.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForecastDetailsViewModel : ViewModel() {

    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    fun processArgs(args: ForecastDetailsFragmentArgs) {
        //https://youtu.be/cWEVIOm-ipo?t=2317
    }
}