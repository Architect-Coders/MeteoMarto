package com.apps.albertmartorell.meteomarto.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.databinding.LytFrgCityForecastBinding
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel.UiForecastModel
import com.apps.albertmartorell.meteomarto.ui.city.Landing
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FrgCityForecast : Fragment() {

    lateinit var binding: LytFrgCityForecastBinding
    lateinit var root: ConstraintLayout
    lateinit var navController: NavController
    private val viewModel: CityViewModel by sharedViewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ForecastRecyclerAdapter
    val args: FrgCityForecastArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        linearLayoutManager = LinearLayoutManager(activity)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        customizeDataBinding(inflater, container)
        binding.lytFrgCityForecastRecycler.layoutManager = linearLayoutManager

        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        observeUI()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        customizeToolBar(view)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {

                navController.navigateUp()

            }

        }

        return super.onOptionsItemSelected(item)

    }

    private fun customizeToolBar(view: View) {

        navController = view.findNavController()
        (activity as Landing).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as Landing).supportActionBar?.title = args.name
        (activity as Landing).supportActionBar?.subtitle = getString(R.string.subtitle_forecast)

    }

    private fun customizeDataBinding(inflater: LayoutInflater, container: ViewGroup?) {

        // The third parameter of inflate method specifies whether the inflated fragment should be added to the container.
        // The container is the parent view that will hold the fragment’s view hierarchy.
        // You should always set this to false when inflating a view for a fragment: The FragmentManager will take care of adding the fragment to the container.
        binding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_frg_city_forecast, container, false)
        root = binding.root as ConstraintLayout
        binding.lifecycleOwner = viewLifecycleOwner

    }

    private fun observeUI() {

        viewModel.eventRequestForecast.observe(
            viewLifecycleOwner,
            Observer { forecastCity ->

                when (forecastCity) {

                    is UiForecastModel.Loading -> {

                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.startRequestForecast(
                            viewModel.eventCityWeather.value?.peekContent()?.latitude,
                            viewModel.eventCityWeather.value?.peekContent()?.longitude
                        )

                    }

                    is UiForecastModel.SuccessRequest -> {

                        adapter = ForecastRecyclerAdapter(forecastCity.listForecast)
                        binding.lytFrgCityForecastRecycler.adapter = adapter
                        viewModel.resetRequestForecast()
                        binding.progressBar.visibility = View.GONE

                    }

                    is UiForecastModel.FinishedWithError -> {

                        binding.lytFrgCityForecastEmptyState.viewStub?.visibility = View.VISIBLE
                        viewModel.resetRequestForecast()
                        binding.progressBar.visibility = View.GONE

                    }

                }

            })

    }

}
