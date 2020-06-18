package com.bowens.ad340.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat

private val DATE_FORMAT = SimpleDateFormat("mm-dd-yyy")
class ForecastDetailsViewModel : ViewModel() {

    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    fun processArgs(args: ForecastDetailsFragmentArgs) {
        //https://youtu.be/cWEVIOm-ipo?t=2317
//        _viewState = ForecastDetailsViewState(
//            temp = args.temp,
//            description = args.description,
//            date = DATE_FORMAT.format(args.date))
    }
}