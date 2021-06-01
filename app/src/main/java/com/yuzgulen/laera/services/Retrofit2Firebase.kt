package com.yuzgulen.laera.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit2Firebase {
    fun get(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://laera-305220-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}