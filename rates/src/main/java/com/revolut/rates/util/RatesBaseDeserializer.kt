package com.revolut.rates.util

import com.google.gson.*
import com.revolut.rates.repository.model.Rate
import com.revolut.rates.repository.model.RateBase
import java.lang.reflect.Type

class RatesBaseDeserializer : JsonDeserializer<RateBase> {

    private val KEY_BASE = "base"
    private val KEY_DATE = "date"
    private val KEY_RATES = "rates"

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RateBase {
        val jsonObj = json?.asJsonObject

        return RateBase(
            jsonObj?.get(KEY_BASE)?.asString,
            jsonObj?.get(KEY_DATE)?.asString,
            mapRates(jsonObj?.get(KEY_RATES)?.asJsonObject)
        )
    }


    private fun mapRates(jsonObject: JsonObject?): List<Rate> {
        val ratesList = mutableListOf<Rate>()
        jsonObject?.keySet()?.forEach {
            ratesList.add(
                Rate(
                    it,
                    jsonObject.get(it).asDouble
                )
            )
        }
        return ratesList
    }
}