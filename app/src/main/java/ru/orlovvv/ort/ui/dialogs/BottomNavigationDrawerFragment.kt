package ru.orlovvv.ort.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_ort.*
import ru.orlovvv.ort.databinding.FragmentBottomNavigationDrawerBinding
import ru.orlovvv.ort.ui.OrtViewModel

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomNavigationDrawerBinding
    private lateinit var bottomDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomNavigationDrawerBinding.inflate(inflater, container, false)

        binding.navMenu.setupWithNavController(nav_host_fragment.findNavController())

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomDialog = super.onCreateDialog(savedInstanceState)
        bottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return bottomDialog
    }

}