package com.revolut.rates.core

import android.content.Context
import com.revolut.rates.core.di.AppModule
import com.revolut.rates.core.di.DaggerRatesCoreComponent
import com.revolut.rates.core.di.RatesCoreComponent

object RatesAppModule {

    lateinit var ratesCoreComponent: RatesCoreComponent
    lateinit var appContext: Context

    fun onCreate(context: Context) {
        appContext = context
        ratesCoreComponent = DaggerRatesCoreComponent.builder().appModule(AppModule(appContext)).build()
    }

}