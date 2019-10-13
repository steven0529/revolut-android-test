package com.revolut.rates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolut.rates.R
import com.revolut.rates.core.RatesAppModule
import com.revolut.rates.core.network.Outcome
import com.revolut.rates.repository.model.Rate
import com.revolut.rates.ui.adapter.RateAdapter
import com.revolut.rates.ui.di.DaggerRatesComponent
import com.revolut.rates.ui.viewmodel.RatesViewModel
import com.revolut.rates.ui.viewmodel.RatesViewModelFactory
import kotlinx.android.synthetic.main.fragment_rate.*
import javax.inject.Inject

class RatesFragment : Fragment(), RateAdapterContract {

    private var holdUpdate: Boolean = false
    private var currentPos: Int = -1
    private var baseRate: Rate = Rate("EUR", 1.0)

    private val ratesComponent by lazy {
        DaggerRatesComponent.builder().ratesCoreComponent(RatesAppModule.ratesCoreComponent).build()
    }

    @Inject
    lateinit var viewModelFactory: RatesViewModelFactory

    private val viewModel: RatesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RatesViewModel::class.java)
    }

    private lateinit var ratesAdapter: RateAdapter

    companion object {
        fun newInstance() = RatesFragment()
        const val FRAGMENT_TAG = "RatesFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_rate, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ratesComponent.inject(this)

        val ratesLlm = LinearLayoutManager(context)
        rv_rates.layoutManager = ratesLlm
        ratesAdapter = RateAdapter(mutableListOf(), this)
        rv_rates.adapter = ratesAdapter

        viewModel.getRatesOutcome.observe(this, Observer { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    if (!holdUpdate) {
                        val ratesList = mutableListOf<Rate>()
                        ratesList.add(baseRate)
                        ratesList.addAll(outcome.data.ratesList)
                        if (currentPos == -1) {
                            ratesAdapter.updateRates(ratesList)
                        } else {
                            ratesAdapter.updateRates(ratesList, currentPos)
                        }
                    }
                }
            }
        })
        viewModel.getRates(ratesAdapter.selectedRate.currency)
    }

    override fun onClickRate(rate: Rate) {
        baseRate = rate
        currentPos = -1
        rv_rates.smoothScrollToPosition(0)
        viewModel.getRates(rate.currency)
    }

    override fun onBaseRateChanged(position: Int) {
        currentPos = position
        rv_rates.post {
            when {
                position > 0 && position < ratesAdapter.itemCount -> {
                    ratesAdapter.notifyItemRangeChanged(0, position)
                    ratesAdapter.notifyItemRangeChanged(position, ratesAdapter.itemCount - position)
                }
                position == 0 -> {
                    ratesAdapter.notifyItemRangeChanged(1, ratesAdapter.itemCount)
                }
                position == ratesAdapter.itemCount -> {
                    ratesAdapter.notifyItemRangeChanged(0, ratesAdapter.itemCount - 2)
                }
            }
        }
    }

    override fun holdUpdate(holdUpdate: Boolean) {
        this.holdUpdate = holdUpdate
    }

    override fun onFocusChange(focus: Boolean, position: Int) {
        currentPos = if (focus)
            position
        else
            -1
    }

}