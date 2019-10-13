package com.revolut.rates.core.di

import com.google.gson.reflect.TypeToken
import com.revolut.rates.BuildConfig.RATES_BASE_URL
import com.revolut.rates.repository.model.RateBase
import com.revolut.rates.util.RatesBaseDeserializer
import com.revolut.rates.util.RatesDeserializerBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


@Module
class RatesNetworkModule{

    @Provides
    @Reusable
    internal fun provideRatesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RATES_BASE_URL)
            .addConverterFactory(RatesDeserializerBuilder.createGsonConverter(
                object : TypeToken<RateBase>() {

                }.type, RatesBaseDeserializer()
            ))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()
    }

}