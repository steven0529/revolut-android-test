package com.revolut.rates.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.revolut.rates.core.extensions.toLiveData
import com.revolut.rates.core.network.Outcome
import com.revolut.rates.repository.RatesContract
import com.revolut.rates.repository.model.RateBase
import io.reactivex.disposables.CompositeDisposable

class RatesViewModel(
    private val ratesRepository: RatesContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val getRatesOutcome: LiveData<Outcome<RateBase>> by lazy {
        ratesRepository.ratesFetchOutcome.toLiveData(compositeDisposable)
    }

    fun getRates(base: String) {
        ratesRepository.fetchRates(base)
    }

}