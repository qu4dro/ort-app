package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentLoadingBinding
import ru.orlovvv.ort.ui.CoordinatesViewModel
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel
import ru.orlovvv.ort.util.Resource

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var coordinatesViewModel: CoordinatesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoadingBinding.bind(view)

        ortViewModel = (activity as OrtActivity).ortViewModel
        coordinatesViewModel = (activity as OrtActivity).coordinatesViewModel

        binding.apply {
            lifecycleOwner = this@LoadingFragment
        }

        // work on this in progress

    }

}