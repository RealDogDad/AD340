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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bowens.ad340.*
import com.bowens.ad340.R.layout.fragment_weekly_forecast
import com.bowens.ad340.api.DailyForecast
import com.bowens.ad340.api.WeeklyForecast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 */
private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class WeeklyForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(fragment_weekly_forecast, container, false)
        val emptyText = view.findViewById<TextView>(R.id.emptyText)
        val progressbar = view.findViewById<ProgressBar>(R.id.progressBar)
        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
           showLocationEntry()
        }

        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecast ->
            showForecastDetails(forecast)
        }
        forecastList.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<WeeklyForecast> { WeeklyForecast ->
            //update the list adapter.
            emptyText.visibility = View.GONE
            progressbar.visibility = View.GONE
            dailyForecastAdapter.submitList(WeeklyForecast.daily)

        }
        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {saveLocation ->
            when (saveLocation) {
                is Location.Zipcode -> {
                    progressbar.visibility = View.VISIBLE
                    forecastRepository.loadWeeklyForecast(saveLocation.Zipcode)
                }

            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)
        return view
    }

    private fun showLocationEntry(){
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast) {
        val temp = forecast.temp.max
        val description = forecast.weather[0].description
        val date = forecast.date
        val iconId = forecast.weather[0].icon
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(temp,description, date,iconId)
        findNavController().navigate(action)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String): WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }

}
