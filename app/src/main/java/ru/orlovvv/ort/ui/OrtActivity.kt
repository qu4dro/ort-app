package ru.orlovvv.ort.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_ort.*
import ru.orlovvv.ort.databinding.ActivityOrtBinding

class OrtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrtBinding.inflate(layoutInflater)
        binding.bnMenu.setupWithNavController(nav_host_fragment.findNavController())

        setContentView(binding.root)

    }
}