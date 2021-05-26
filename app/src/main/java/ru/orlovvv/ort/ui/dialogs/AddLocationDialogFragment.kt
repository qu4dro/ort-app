package ru.orlovvv.ort.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentAddLocationDialogBinding
import ru.orlovvv.ort.models.LocationPost
import ru.orlovvv.ort.ui.LocationViewModel
import ru.orlovvv.ort.ui.OrtViewModel
import ru.orlovvv.ort.util.LocationUtility


@AndroidEntryPoint
class AddLocationDialogFragment : BottomSheetDialogFragment() {

    private val ortViewModel: OrtViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()
    private lateinit var binding: FragmentAddLocationDialogBinding
    private lateinit var bottomDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddLocationDialogBinding.inflate(inflater, container, false)

        binding.apply {
            ivGetAddress.setOnClickListener {
                binding.etLocationAddress.setText(LocationUtility.getAddressString(locationViewModel.locationLiveData.value!!, requireContext()))
            }
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

        binding.ivCloseDialog.setOnClickListener {
            bottomDialog.dismiss()
        }

        binding.btnCreateLocation.setOnClickListener {

            if (binding.etLocationName.text.toString().isEmpty()) binding.tilLocationName.error =
                getString(R.string.required)
            if (binding.etLocationTags.text.toString().isEmpty()) binding.tilLocationTags.error =
                getString(R.string.required)

            if (binding.etLocationName.text.toString()
                    .isNotEmpty() && binding.etLocationTags.text.toString().isNotEmpty()
            ) {
                createLocation()
                bottomDialog.dismiss()
                ortViewModel.getNearbyLocationsFromServer(locationViewModel.locationLiveData.value!!)
            }

            Handler(Looper.getMainLooper()).postDelayed({
                binding.tilLocationName.error = null
                binding.tilLocationTags.error = null
            }, 2000)

        }

    }

    private fun createLocation() {
        ortViewModel.addLocation(
            LocationPost(
                binding.etLocationName.text.toString(),
                binding.etLocationAddress.text.toString(),
                binding.etLocationTags.text.toString(),
                locationViewModel.locationLiveData.value!!.lng,
                locationViewModel.locationLiveData.value!!.lat,
            )
        )
    }

}