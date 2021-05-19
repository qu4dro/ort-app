package ru.orlovvv.ort.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomDialog = super.onCreateDialog(savedInstanceState)
        bottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bottomDialog.setOnShowListener {
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> dismiss()
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

        return bottomDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivCloseDialog.setOnClickListener{
            bottomDialog.dismiss()
        }
        binding.btnCreateLocation.setOnClickListener {
            if (binding.etLocationName.text.toString().isEmpty()) binding.tilLocationName.error =
                R.string.required.toString()
            if (binding.etLocationTags.text.toString().isEmpty()) binding.tilLocationTags.error =
                R.string.required.toString()
            if (binding.etLocationName.text.toString()
                    .isNotEmpty() && binding.etLocationTags.text.toString().isNotEmpty()
            ) {

                ortViewModel.addLocation(
                    LocationPost(
                        binding.etLocationName.text.toString(),
                        binding.tvLocationAddress.text.toString(),
                        binding.etLocationTags.text.toString(),
                        104.2997634,
                        52.2225774,
                        binding.etLocationDate.text.toString()
                    )
                )

            }

            bottomDialog.dismiss()

        }

        binding.etLocationName.setOnClickListener {
            binding.tilLocationName.error = null
        }

        binding.etLocationTags.setOnClickListener {
            binding.tilLocationTags.error = null
        }
    }

}