package ru.orlovvv.ort.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_ort.*
import ru.orlovvv.ort.databinding.ActivityOrtBinding
import ru.orlovvv.ort.db.OrtDatabase
import ru.orlovvv.ort.repository.OrtRepository

class OrtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrtBinding
    private lateinit var _ortViewModel: OrtViewModel
    val ortViewModel: OrtViewModel
        get() = _ortViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrtBinding.inflate(layoutInflater)
        binding.bnMenu.setupWithNavController(nav_host_fragment.findNavController())

        val ortDatabase = OrtDatabase(this)
        val ortRepository = OrtRepository(ortDatabase)
        val ortViewModelProviderFactory = OrtViewModelProviderFactory(ortRepository)
        _ortViewModel =
            ViewModelProvider(this, ortViewModelProviderFactory).get(OrtViewModel::class.java)

        setContentView(binding.root)

    }
}