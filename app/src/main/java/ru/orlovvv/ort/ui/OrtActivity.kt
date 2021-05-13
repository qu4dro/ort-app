package ru.orlovvv.ort.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.ActivityMainBinding

class OrtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bnMenu.setupWithNavController(nav_host_fragment.findNavController())

        setContentView(binding.root)

    }
}