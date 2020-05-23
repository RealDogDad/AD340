package com.bowens.ad340.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bowens.ad340.AppNavigator

import com.bowens.ad340.R

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        //update UI, get view references
        var zipcodeEditText: EditText = view.findViewById(R.id.zip)
        var submitButton: Button = view.findViewById(R.id.submit)

        submitButton.setOnClickListener {
            val zipCode: String = zipcodeEditText.text.toString()
            if(zipCode.length != 5) {
                Toast.makeText(requireContext(), "Please enter valid Zip Code", Toast.LENGTH_SHORT).show()
            }
            else {
                appNavigator.navigateToCurrentForecast(zipCode)
            }
        }


        return view
    }

}
