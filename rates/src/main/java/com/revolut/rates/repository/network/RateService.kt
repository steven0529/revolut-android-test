package com.revolut.rates.repository.network

import com.revolut.rates.repository.model.RateBase
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RateService {

    @GET("latest")
    fun getLatestRates(@Query("base") base: String) : Observable<RateBase>

}