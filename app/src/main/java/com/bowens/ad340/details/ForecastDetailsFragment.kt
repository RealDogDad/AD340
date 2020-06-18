package com.bowens.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bowens.ad340.TempDisplaySettingManager
import com.bowens.ad340.databinding.FragmentForecastDetailsBinding
import com.bowens.ad340.formatTempForDisplay

class ForecastDetailsFragment : Fragment() {
    private val args: ForecastDetailsFragmentArgs by navArgs()
    private val viewModel = ForecastDetailsViewModel()

    private var _binding: FragmentForecastDetailsBinding? = null
    // This property only valid between onCreateView and onDestroyView
    private val binding get()= _binding!!

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        binding.tempText.text =
            formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        binding.descriptionText.text = args.description
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState> { viewState ->
            //update UI
        }
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
