package com.revolut.rates.ui.di

import com.revolut.rates.core.network.Scheduler
import com.revolut.rates.repository.RatesContract
import com.revolut.rates.repository.RatesRepository
import com.revolut.rates.repository.network.RateRemoteData
import com.revolut.rates.repository.network.RateService
import com.revolut.rates.ui.viewmodel.RatesViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
class RatesModule {

    @Provides
    @RatesScope
    fun ratesViewModelFactory(
        ratesRepository: RatesContract.Repository,
        compositeDisposable: CompositeDisposable
    ): RatesViewModelFactory = RatesViewModelFactory(ratesRepository, compositeDisposable)

    @Provides
    @RatesScope
    fun ratesRepo(remote: RatesContract.Remote, scheduler: Scheduler,
                  compositeDisposable: CompositeDisposable) :
            RatesContract.Repository = RatesRepository(remote, scheduler, compositeDisposable)

    @Provides
    @RatesScope
    fun createRatesRemoteData(rateService: RateService)
            : RatesContract.Remote
            = RateRemoteData(rateService)

    @Provides
    @RatesScope
    fun ratesService(retrofit: Retrofit): RateService
            = retrofit.create(RateService::class.java)

    @Provides
    @RatesScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

}