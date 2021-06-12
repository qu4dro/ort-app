package ru.orlovvv.ort.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentBottomNavigationDrawerBinding
import ru.orlovvv.ort.ui.OrtActivity

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomNavigationDrawerBinding
    private lateinit var bottomDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomNavigationDrawerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController: NavController
        (activity as OrtActivity).apply {
            navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        }
        binding.navMenu.setupWithNavController(navController)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomDialog = super.onCreateDialog(savedInstanceState)
        bottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return bottomDialog
    }

}