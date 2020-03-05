package com.apps.albertmartorell.meteomarto.ui.forecast

import albertmartorell.com.domain.cityforecast.ForecastDomain
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.ui.inflate
import com.apps.albertmartorell.meteomarto.ui.loadIconsWeather
import kotlinx.android.synthetic.main.lyt_frg_city_forecast_recycler_row.view.*

class ForecastRecyclerAdapter(private val forecastList: List<ForecastDomain>) :
    RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder>() {

    // It is called when there no ViewHolders available. In this case, the RecyclerView will as this event to make a new one (ForecastHolder)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastHolder {

        // Inflate a view from a layout, and the it is passed it to the ForecastHolder
        val inflatedView = parent.inflate(R.layout.lyt_frg_city_forecast_recycler_row, false)
        return ForecastHolder(inflatedView)

    }

    override fun getItemCount(): Int = forecastList.size

    // It tells us how to show the right forecast at the right moment. It lets us know a new item will be available on screen and the holder needs some data
    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {

        // We are passing a copy of ForecastHolder and the position where the item will show in the RecyclerView and then calling the bind method
        val forecastItem = forecastList.get(position)
        holder.bind(forecastItem)

    }

    // ViewHolder is in charge of setting the value to the views
    class ForecastHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var forecastDomain: ForecastDomain? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {

            Log.d("RecyclerView", "CLICK!")

        }

        /**
         * It binds the forecast to the ForecastHolder, giving the data it needs to workout that it should show
         */
        fun bind(_forecastDomain: ForecastDomain) {

            forecastDomain = _forecastDomain
            view.lyt_frg_city_forecast_recycler_row_time.text = forecastDomain?.time
            view.lyt_frg_city_forecast_recycler_row_icon.loadIconsWeather(forecastDomain?.icon)
            view.lyt_frg_city_forecast_recycler_row_description.text = forecastDomain?.description
            view.lyt_frg_city_forecast_recycler_row_temp_min.text = view.context.getString(
                R.string.min_temperature,
                forecastDomain?.temperatureMin.toString()
            )

            view.lyt_frg_city_forecast_recycler_row_temp_max.text = view.context.getString(
                R.string.max_temperature,
                forecastDomain?.temperatureMax.toString()
            )
            view.lyt_frg_city_forecast_recycler_row_temp_feel.text = view.context.getString(
                R.string.feels_like,
                forecastDomain?.temperatureFeelsLike.toString()
            )

        }

    }

}