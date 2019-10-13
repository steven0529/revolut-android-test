package com.revolut.rates.ui.adapter

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.revolut.rates.R
import com.revolut.rates.core.extensions.placeCursorToEnd
import com.revolut.rates.repository.model.Rate
import com.revolut.rates.ui.RateAdapterContract
import com.revolut.rates.util.*
import kotlinx.android.synthetic.main.item_rate.view.*
import java.text.DecimalFormat

class RateAdapter(
    private var rates: List<Rate>,
    private val rateAdapterContract: RateAdapterContract
) : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    private val dec = DecimalFormat(RATE_DATE_FORMAT)
    private var multiplier: Double = 1.0
    var selectedRate: Rate = Rate("EUR",1.0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val contentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rate, parent, false)
        return RateViewHolder(contentView, RateChangeTextWatcher())
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.rateChangeTextWatcher.setPosition(position)
        holder.rateChangeTextWatcher.setRate(rates[position])
        holder.bind(rates[position], position)
    }

    fun updateRates(rates: List<Rate>) {
        this.rates = rates
        notifyDataSetChanged()
    }

    fun updateRates(rates: List<Rate>, position: Int) {
        this.rates = rates
        when (position) {
            in 1 until (itemCount - 1) -> {
                notifyItemRangeChanged(0, position )
                notifyItemRangeChanged(position + 1, itemCount - position)
            }
            0 -> {
                notifyItemRangeChanged(1, itemCount)
            }
            itemCount -> {
                notifyItemRangeChanged(0, itemCount - 2)
            }
        }
    }

    inner class RateViewHolder(
        itemView: View, val rateChangeTextWatcher: RateChangeTextWatcher
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(rate: Rate, position: Int) = with(itemView) {
            tv_symbol.text = rate.currency

            val flagIconResId = resources.getIdentifier(
                ICON_PREFIX
                        + rate.currency.toLowerCase(), DRAWABLE, context?.packageName
            )
            Glide.with(context)
                .load(flagIconResId)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .fitCenter()
                )
                .into(iv_flag)
            val currencyNameResId = resources.getIdentifier(
                rate.currency.toLowerCase()
                        + LABEL_SUFFIX, STRING, context?.packageName
            )
            tv_name.setText(currencyNameResId)
            container_rate_row.setOnClickListener {
                rateAdapterContract.onClickRate(rate)
            }
            rateChangeTextWatcher.setEtRate(et_rate)
            et_rate.addTextChangedListener(rateChangeTextWatcher)
            et_rate.setOnClickListener {
                rateAdapterContract.holdUpdate(true)
            }

            et_rate.setOnFocusChangeListener { _, focused ->
                if (focused)
                    et_rate.placeCursorToEnd()
                rateAdapterContract.onFocusChange(focused, position)
            }

            if (!et_rate.isFocused) {
                et_rate.setText(dec.format(rate.rate * multiplier))
            }

            if (multiplier == 0.0)
                et_rate.setText("")

        }
    }

    inner class RateChangeTextWatcher : TextWatcher {

        private lateinit var etRate: EditText
        private lateinit var rate: Rate
        private var position: Int = 0

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (etRate.isFocused && !p0.isNullOrBlank()) {
                selectedRate = rate
                multiplier = p0.toString().replace(",", "").toDouble()
                rateAdapterContract.onBaseRateChanged(position)
                Handler().postDelayed({
                    rateAdapterContract.holdUpdate(false)
                }, 1000)
            } else if (etRate.isFocused && p0.isNullOrBlank()) {
                selectedRate = rate
                multiplier = 0.0
                rateAdapterContract.onBaseRateChanged(position)
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            // Do nothing
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Do nothing
        }

        fun setPosition(position: Int) {
            this.position = position
        }

        fun setEtRate(etRate: EditText) {
            this.etRate = etRate
        }

        fun setRate(rate: Rate) {
            this.rate = rate
        }
    }

}