package com.bowens.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bowens.ad340.*
import com.bowens.ad340.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 */
private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")
class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        val locationName = view.findViewById<TextView>(R.id.locationName)
        val tempText= view.findViewById<TextView>(R.id.tempText)
        val icon = view.findViewById<ImageView>(R.id.forecastIcon)
        val date = view.findViewById<TextView>(R.id.dateText)


        //Zipcode entry
        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""
        //Temp Unit Display
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())


        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }


        val currentWeatherObserver = Observer<CurrentWeather> { weather ->

            locationName.text = weather.name
//            val iconId = weather
//            val date = DATE_FORMAT
            tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())

        }

        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(savedLocation.Zipcode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return view
    }


    private fun showLocationEntry(){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String) : CurrentForecastFragment {
            val fragment= CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }

}
