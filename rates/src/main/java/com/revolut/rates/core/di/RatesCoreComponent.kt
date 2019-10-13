package com.revolut.rates.core.di

import android.content.Context
import com.revolut.rates.core.network.Scheduler
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RatesNetworkModule::class])
interface RatesCoreComponent {

    fun ratesRetrofit(): Retrofit

    fun scheduler(): Scheduler

    fun context(): Context
}