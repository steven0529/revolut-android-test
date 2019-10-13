package com.revolut.rates.repository.model

import com.google.gson.annotations.SerializedName

data class RateBase(
    @SerializedName("base") val baseCurrency: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("rates")var ratesList: List<Rate>
)

data class Rate (
    val currency: String,
    var rate: Double
)