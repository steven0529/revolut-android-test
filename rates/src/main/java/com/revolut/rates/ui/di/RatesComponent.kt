package com.revolut.rates.ui.di

import com.revolut.rates.core.di.RatesCoreComponent
import com.revolut.rates.ui.RatesFragment
import dagger.Component

@RatesScope
@Component(dependencies = [RatesCoreComponent::class], modules = [RatesModule::class])
interface RatesComponent {

    fun inject(ratesFragment: RatesFragment)

}