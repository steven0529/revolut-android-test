package com.revolut.rates.repository

import com.revolut.rates.core.network.Outcome
import com.revolut.rates.repository.model.RateBase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface RatesContract {

    interface Repository {
        val ratesFetchOutcome: PublishSubject<Outcome<RateBase>>
        fun fetchRates(base: String)
    }

    interface Remote {
        fun getRates(base: String) : Observable<RateBase>
    }
}