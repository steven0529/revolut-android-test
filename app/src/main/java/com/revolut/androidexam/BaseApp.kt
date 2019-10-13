package com.revolut.androidexam

import android.app.Application
import com.revolut.rates.core.RatesAppModule

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RatesAppModule.onCreate(this)
    }
}