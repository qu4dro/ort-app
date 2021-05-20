package ru.orlovvv.ort.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentAddLocationDialogBinding
import ru.orlovvv.ort.models.LocationPost
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class AddLocationDialogFragment : BottomSheetDialogFragment() {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentAddLocationDialogBinding
    private lateinit var bottomDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddLocationDialogBinding.inflate(inflater, container, false)
        ortViewModel = (activity as OrtActivity).ortViewModel

        binding.apply {
            lifecycleOwner = this@AddLocationDialogFragment
            viewModel = ortViewModel
        }

        binding.etLocationAddress.setText(ortViewModel.currentAddressString.value)

        binding.ivGetAddress.setOnClickListener {
            binding.etLocationAddress.setText(ortViewModel.currentAddressString.value)
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomDialog = super.onCreateDialog(savedInstanceState)
        bottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setOnShowDialogListener()

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
                ortViewModel.getNearbyLocationsFromServer()
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
                ortViewModel.lng.value!!,
                ortViewModel.lat.value!!
            )
        )
    }

    private fun setOnShowDialogListener() {
        bottomDialog.setOnShowListener {
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
//                        BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > 0.5) {
                        binding.ivCloseDialog.visibility = View.VISIBLE
                    } else {
                        binding.ivCloseDialog.visibility = View.GONE
                    }
                }
            })
        }
    }
}