package com.revolut.rates.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revolut.rates.repository.RatesContract
import com.revolut.rates.repository.RatesRepository
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class RatesViewModelFactory(
    private val ratesRepository: RatesContract.Repository,
    private val compositeDisposable: CompositeDisposable
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RatesViewModel(ratesRepository, compositeDisposable) as T
    }
}