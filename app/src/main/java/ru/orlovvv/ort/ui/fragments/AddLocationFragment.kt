package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentAddLocationBinding
import ru.orlovvv.ort.databinding.FragmentLocationInfoBinding
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class AddLocationFragment : Fragment(R.layout.fragment_add_location) {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentAddLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddLocationBinding.inflate(inflater)
        ortViewModel = (activity as OrtActivity).ortViewModel

        binding.apply {
            lifecycleOwner = this@AddLocationFragment
            viewModel = ortViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateLocation.setOnClickListener {
            if (binding.etLocationName.text.toString().isEmpty()) binding.tilLocationName.error =
                R.string.required.toString()
            if (binding.etLocationTags.text.toString().isEmpty()) binding.tilLocationTags.error =
                R.string.required.toString()
            if (binding.etLocationName.text.toString()
                    .isNotEmpty() && binding.etLocationTags.text.toString().isNotEmpty()) {
                findNavController().navigate(R.id.action_addLocationFragment_to_nearbyLocationsFragment)
            }
        }

        binding.etLocationName.setOnClickListener {
            binding.tilLocationName.error = null
        }

        binding.etLocationTags.setOnClickListener {
            binding.tilLocationTags.error = null
        }
    }

}