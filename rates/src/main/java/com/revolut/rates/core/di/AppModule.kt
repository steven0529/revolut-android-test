package com.revolut.rates.core.di

import android.content.Context
import com.revolut.rates.core.network.AppScheduler
import com.revolut.rates.core.network.Scheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideScheduler(): Scheduler {
        return AppScheduler()
    }
}