package com.apps.albertmartorell.meteomarto.ui.city

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.databinding.LytActLandingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class Landing : AppCompatActivity() {

    private lateinit var binding: LytActLandingBinding
    val viewModel: CityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.lyt_act_landing)
        binding.lifecycleOwner = this
        customizeToolbar()

    }

    private fun customizeToolbar() {

        setSupportActionBar(binding.toolbar)

    }

}
