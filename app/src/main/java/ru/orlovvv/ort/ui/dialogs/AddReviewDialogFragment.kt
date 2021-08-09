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
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentAddReviewDialogBinding
import ru.orlovvv.ort.models.ReviewPost
import ru.orlovvv.ort.viewmodels.NearbyLocationsViewModel
import ru.orlovvv.ort.viewmodels.SavedLocationsViewModel

@AndroidEntryPoint
class AddReviewDialogFragment : BottomSheetDialogFragment() {

    private val nearbyLocationsViewModel: NearbyLocationsViewModel by activityViewModels()

    private var _binding: FragmentAddReviewDialogBinding? = null
    private val binding
        get() = _binding!!

    private var _bottomDialog: Dialog? = null
    private val bottomDialog
        get() = _bottomDialog!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddReviewDialogBinding.inflate(inflater, container, false)

        val items = listOf("5", "4", "3", "2", "1")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.tilRating.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _bottomDialog = super.onCreateDialog(savedInstanceState)
        bottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return bottomDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivCloseDialog.setOnClickListener {
            bottomDialog.dismiss()
        }

        binding.btnCreateReview.setOnClickListener {

            if (binding.etAuthorName.text.toString().isEmpty()) binding.tilAuthorName.error =
                getString(R.string.required)
            if (binding.etReviewText.text.toString().isEmpty()) binding.tilReviewText.error =
                getString(R.string.required)

            if (binding.etReviewText.text.toString()
                    .isNotEmpty() && binding.etAuthorName.text.toString().isNotEmpty()
            ) {
                addReview()
                bottomDialog.dismiss()
                nearbyLocationsViewModel.getLocationInfo(nearbyLocationsViewModel.currentLocationInfo.value?.data!!._id)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.tilAuthorName.error = null
                    binding.tilReviewText.error = null
                }, 2000)
            }
        }

    }

    private fun addReview() {
        nearbyLocationsViewModel.addReview(
            ReviewPost(
                binding.etAuthorName.text.toString(),
                binding.etReviewText.text.toString(),
                binding.acRating.text.toString()
            ), nearbyLocationsViewModel.currentLocationInfo.value?.data!!._id
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _bottomDialog = null
    }

}