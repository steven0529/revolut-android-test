package com.revolut.rates.ui

import com.revolut.rates.repository.model.Rate

interface RateAdapterContract {

    fun onBaseRateChanged(position: Int)
    fun onClickRate(rate: Rate)
    fun holdUpdate(holdUpdate: Boolean)
    fun onFocusChange(focus: Boolean, position: Int)
}