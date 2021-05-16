package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

}