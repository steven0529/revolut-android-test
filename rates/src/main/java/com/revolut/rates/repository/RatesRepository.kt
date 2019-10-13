package com.revolut.rates.repository

import com.revolut.rates.core.extensions.addTo
import com.revolut.rates.core.extensions.performOnBackOutOnMain
import com.revolut.rates.core.extensions.failed
import com.revolut.rates.core.extensions.loading
import com.revolut.rates.core.extensions.success
import com.revolut.rates.core.network.Outcome
import com.revolut.rates.core.network.Scheduler
import com.revolut.rates.repository.model.RateBase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class RatesRepository(
    private val remote: RatesContract.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : RatesContract.Repository {

    override val ratesFetchOutcome: PublishSubject<Outcome<RateBase>> = PublishSubject.create()

    var ratesIntervalDisposable: Disposable? = null

    override fun fetchRates(base: String) {
        ratesFetchOutcome.loading(true)
        ratesIntervalDisposable?.dispose()
        this.ratesIntervalDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .map {
                remote.getRates(base)
            }
            .performOnBackOutOnMain(scheduler)
            .subscribe({
                it.performOnBackOutOnMain(scheduler)
                    .subscribe({
                        ratesFetchOutcome.success(it)
                    }, { error ->
                        ratesFetchOutcome.failed(error)
                    })
                    .addTo(compositeDisposable)
            }, { error ->
                ratesFetchOutcome.failed(error)
            })
    }

}