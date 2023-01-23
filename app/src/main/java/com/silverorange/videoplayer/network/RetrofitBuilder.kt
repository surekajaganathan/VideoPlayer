package com.silverorange.videoplayer.network

import com.silverorange.videoplayer.util.Url
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Url.BASE_URL)
            .build()
    }
}

