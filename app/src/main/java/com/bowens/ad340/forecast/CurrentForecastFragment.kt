package com.bowens.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bowens.ad340.*
import com.bowens.ad340.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

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
        val dateText = view.findViewById<TextView>(R.id.dateText)
        val emptyText = view.findViewById<TextView>(R.id.emptyText)
        val progressbar = view.findViewById<ProgressBar>(R.id.progressBar)


        //Zipcode entry
        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""
        //Temp Unit Display
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())


        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }


        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            emptyText.visibility = View.GONE
            progressbar.visibility = View.GONE
            dateText.visibility = View.VISIBLE
            locationName.visibility = View.VISIBLE
            tempText.visibility = View.VISIBLE
            locationName.text = weather.name
            dateText.text = DATE_FORMAT.format(Date())
            tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        }

        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {savedLocation ->
            when (savedLocation) {
                is Location.Zipcode ->  {
                    progressbar.visibility = View.VISIBLE
                    forecastRepository.loadCurrentForecast(savedLocation.Zipcode)

                }
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
