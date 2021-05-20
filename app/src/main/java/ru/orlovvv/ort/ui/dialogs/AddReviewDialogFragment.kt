package ru.orlovvv.ort.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentAddLocationDialogBinding
import ru.orlovvv.ort.databinding.FragmentAddReviewDialogBinding
import ru.orlovvv.ort.models.LocationPost
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class AddReviewDialogFragment : BottomSheetDialogFragment() {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentAddReviewDialogBinding
    private lateinit var bottomDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddReviewDialogBinding.inflate(inflater, container, false)
        ortViewModel = (activity as OrtActivity).ortViewModel

        val items = listOf("5", "4", "3", "2", "1")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.tilRating.editText as? AutoCompleteTextView)?.setAdapter(adapter)

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

        binding.btnCreateReview.setOnClickListener {

            if (binding.etAuthorName.text.toString().isEmpty()) binding.etAuthorName.error =
                getString(R.string.required)
            if (binding.etReviewText.text.toString().isEmpty()) binding.etReviewText.error =
                getString(R.string.required)

            if (binding.etReviewText.text.toString()
                    .isNotEmpty() && binding.etAuthorName.text.toString().isNotEmpty()
            ) {
                addReview()
                bottomDialog.dismiss()
            }

            Handler(Looper.getMainLooper()).postDelayed({
                binding.tilAuthorName.error = null
                binding.tilReviewText.error = null
            }, 2000)

        }

    }

    private fun addReview() {


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