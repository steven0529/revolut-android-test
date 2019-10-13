package com.revolut.rates.core.network

import io.reactivex.Scheduler

interface Scheduler {

    fun mainThread() : Scheduler

    fun io() : Scheduler

    fun compute() : Scheduler
}