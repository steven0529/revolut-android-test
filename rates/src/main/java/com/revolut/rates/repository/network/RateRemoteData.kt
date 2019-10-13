package com.revolut.rates.repository.network

import com.revolut.rates.repository.RatesContract
import com.revolut.rates.repository.model.RateBase
import io.reactivex.Observable

class RateRemoteData (private val rateService: RateService) : RatesContract.Remote {

    override fun getRates(base: String): Observable<RateBase> = rateService.getLatestRates(base)

}