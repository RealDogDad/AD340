package com.bowens.ad340.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bowens.ad340.Location
import com.bowens.ad340.LocationRepository

import com.bowens.ad340.R

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        locationRepository = LocationRepository(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        //update UI, get view references
        val zipcodeEditText: EditText = view.findViewById(R.id.zip)
        val submitButton: Button = view.findViewById(R.id.submit)

        submitButton.setOnClickListener {
            val zipCode: String = zipcodeEditText.text.toString()
            if(zipCode.length != 5) {
                Toast.makeText(requireContext(), "Please enter valid Zip Code", Toast.LENGTH_SHORT).show()
            }
            else {
                locationRepository.saveLocation(Location.Zipcode(zipCode))
                findNavController().navigateUp()
            }
        }


        return view
    }

}
