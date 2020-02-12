package com.apps.albertmartorell.meteomarto.ui.city

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.databinding.LytFrgCityWeatherBinding
import com.apps.albertmartorell.meteomarto.ui.PermissionRequester
import com.apps.albertmartorell.meteomarto.ui.model.CityUIView

class FrgCityWeather : Fragment() {

    lateinit var binding: LytFrgCityWeatherBinding
    lateinit var root: ConstraintLayout
    lateinit var navController: NavController
    lateinit var viewModel: CityViewModel
    //private val viewModel by sharedViewModel<CityViewModel>()
    lateinit var coarsedPermissionRequest: PermissionRequester

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(activity as Landing).get(CityViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        customizeToolBar()
        customizeDataBinding(inflater, container)

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        coarsedPermissionRequest =
            PermissionRequester((activity as Landing), Manifest.permission.ACCESS_COARSE_LOCATION)
        observeUI()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        // Inflate the menu to use in the action bar
        //val inflater = menuInflater
        inflater.inflate(R.menu.menu_city_weather, menu)
        val menuItem = menu.findItem(R.id.menu_city_weather_forecast)

        viewModel.eventCityWeather.value?.peekContent()?.latitude.let {

            when (it) {

                0F -> {

                    menuItem.setVisible(false)

                }
                null -> {

                    menuItem.setVisible(false)

                }
                else -> {

                    menuItem.setVisible(true)

                }

            }

        }


        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_city_weather_forecast -> {
                val action = FrgCityWeatherDirections.actionFrgCityWeatherToFrgCityForecast(
                    viewModel.eventCityWeather.value?.peekContent()?.latitude.toString(),
                    viewModel.eventCityWeather.value?.peekContent()?.name,
                    viewModel.eventCityWeather.value?.peekContent()?.longitude.toString()
                )
                navController.navigate(action)

            }

        }

        return super.onOptionsItemSelected(item)

    }

    private fun observeUI() {

        viewModel.eventRequestLocationPermission.observe(
            viewLifecycleOwner,
            Observer {

                it.getContentIfNotHandled()
                    ?.let {

                        makeViewsVisible(View.GONE)
                        binding.progressBar.visibility = View.VISIBLE
                        coarsedPermissionRequest.request { permissionGranted: Boolean ->
                            viewModel.onCoarsePermissionRequested(
                                permissionGranted
                            )
                        }

                    }
            })

        viewModel.eventRequestedLocationPermissionFinished.observe(
            viewLifecycleOwner,
            Observer {

                it.getContentIfNotHandled()?.let {

                    if (it.latitude == 0F || it.longitude == 0F) {

                        viewModel.getCityWeatherNotCoordinates()

                    } else {

                        viewModel.getCityWeatherFromService(it)

                    }

                }

            })

        viewModel.eventPermissionDenied.observe(viewLifecycleOwner, Observer {

            it.getContentIfNotHandled()?.let {

                binding.progressBar.visibility = View.GONE
                binding.lytFrgActPermissionDenied.visibility = View.VISIBLE
                //activity?.invalidateOptionsMenu()

            }

        })

        viewModel.eventPermissionGranted.observe(
            viewLifecycleOwner,
            Observer {

                it.getContentIfNotHandled()?.let {

                    viewModel.getCityWeather()

                }

            })

        viewModel.eventNotLocalData.observe(viewLifecycleOwner, Observer {

            it.getContentIfNotHandled()?.let {

                binding.progressBar.visibility = View.GONE
                binding.lytFrgCityWeatherNoCoordinates.visibility = View.VISIBLE

            }

        })

        viewModel.eventCityWeatherOffline.observe(viewLifecycleOwner, Observer { cityView ->

            cityView.getContentIfNotHandled()?.let { response ->

                showData(response)
                binding.lytFrgCityWeatherNoCoordinates.visibility = View.VISIBLE

            }

        })

        viewModel.eventCityWeather.observe(viewLifecycleOwner, Observer { cityView ->

            cityView.getContentIfNotHandled()?.let { response ->

                showData(response)
                activity?.invalidateOptionsMenu()

            }

        })

    }

    private fun showData(cityUIView: CityUIView) {

        binding.lytFrgCityWeatherDate.text = cityUIView.date.toString()

        binding.lytFrgCityWeatherTempMin.text = activity?.getString(
            R.string.min_temperature,
            cityUIView.temperatureMin.toString()
        )

        binding.lytFrgCityWeatherTempMax.text = activity?.getString(
            R.string.max_temperature,
            cityUIView.temperatureMax.toString()
        )

        binding.lytFrgCityWeatherFeelsLike.text = activity?.getString(
            R.string.feels_like,
            cityUIView.temperatureFeelsLike.toString()
        )
        binding.lytFrgCityWeatherPressure.text = activity?.getString(
            R.string.atmospheric_pressure,
            cityUIView.pressure.toString()
        )

        binding.lytFrgCityWeatherDescription.text = activity?.getString(
            R.string.current_weather,
            cityUIView.description.toString()
        )

        //binding.lytFrgCityWeatherIcon.loadIconsWeather(cityUIView.icon)
        binding.lytFrgCityWeatherName.text = cityUIView.name

        makeViewsVisible(View.VISIBLE)
        binding.progressBar.visibility = View.GONE


    }

    private fun makeViewsVisible(_visibility: Int) {

        binding.lytFrgCityWeatherName.visibility = _visibility
        binding.lytFrgCityWeatherPressure.visibility = _visibility
        binding.lytFrgCityWeatherDate.visibility = _visibility
        binding.lytFrgCityWeatherDescription.visibility = _visibility
        binding.lytFrgCityWeatherIcon.visibility = _visibility
        binding.lytFrgCityWeatherTempMax.visibility = _visibility
        binding.lytFrgCityWeatherTempMin.visibility = _visibility
        binding.lytFrgCityWeatherFeelsLike.visibility = _visibility

    }

    private fun customizeDataBinding(inflater: LayoutInflater, container: ViewGroup?) {

        // The third parameter of inflate method specifies whether the inflated fragment should be added to the container.
        // The container is the parent view that will hold the fragment’s view hierarchy.
        // You should always set this to false when inflating a view for a fragment: The FragmentManager will take care of adding the fragment to the container.
        binding = DataBindingUtil.inflate(inflater, R.layout.lyt_frg_city_weather, container, false)
        root = binding.root as ConstraintLayout
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    private fun customizeToolBar() {

        (activity as Landing).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as Landing).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as Landing).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as Landing).supportActionBar?.title = getString(R.string.toolbar_title)
        (activity as Landing).supportActionBar?.subtitle = ""

    }

}