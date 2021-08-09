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
import ru.orlovvv.ort.viewmodels.CoordinatesViewModel
import ru.orlovvv.ort.util.LocationUtility
import ru.orlovvv.ort.viewmodels.NearbyLocationsViewModel
import ru.orlovvv.ort.viewmodels.SavedLocationsViewModel


@AndroidEntryPoint
class AddLocationDialogFragment : BottomSheetDialogFragment() {

    private val nearbyLocationsViewModel: NearbyLocationsViewModel by activityViewModels()
    private val coordinatesViewModel: CoordinatesViewModel by activityViewModels()

    private var _binding: FragmentAddLocationDialogBinding? = null
    val binding
        get() = _binding!!
    private var _bottomDialog: Dialog? = null

    private val bottomDialog
        get() = _bottomDialog!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddLocationDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _bottomDialog = super.onCreateDialog(savedInstanceState)
        bottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return bottomDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivGetAddress.setOnClickListener {
                binding.etLocationAddress.setText(
                    LocationUtility.getAddressString(
                        coordinatesViewModel.locationLiveData.value!!,
                        requireContext()
                    )
                )
            }
        }

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
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.tilLocationName.error = null
                    binding.tilLocationTags.error = null
                }, 2000)
            }

        }

    }

    private fun createLocation() {
        nearbyLocationsViewModel.addLocation(
            LocationPost(
                binding.etLocationName.text.toString(),
                binding.etLocationAddress.text.toString(),
                binding.etLocationTags.text.toString(),
                coordinatesViewModel.locationLiveData.value!!.lng,
                coordinatesViewModel.locationLiveData.value!!.lat,
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _bottomDialog = null
    }

}