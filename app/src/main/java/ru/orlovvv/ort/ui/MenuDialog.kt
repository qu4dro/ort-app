package ru.orlovvv.ort.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.DialogMenuBinding

class MenuDialog : BottomSheetDialogFragment() {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: DialogMenuBinding
    private lateinit var bottomDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogMenuBinding.inflate(inflater, container, false)
        ortViewModel = (activity as OrtActivity).ortViewModel

        binding.apply {
            lifecycleOwner = this@MenuDialog
            viewModel = ortViewModel
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomDialog = super.onCreateDialog(savedInstanceState)
        bottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return bottomDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNearbyLocationItem.setOnClickListener {
            bottomDialog.dismiss()
            findNavController().navigate(R.id.action_nearbyLocationsFragment_self)
        }

        binding.btnSavedLocationItem.setOnClickListener {
            bottomDialog.dismiss()
            findNavController().navigate(R.id.action_nearbyLocationsFragment_to_savedLocationsFragment)
        }

        binding.btnMapsLocationItem.setOnClickListener {
            bottomDialog.dismiss()
            findNavController().navigate(R.id.action_nearbyLocationsFragment_to_mapsFragment)
        }

    }
}