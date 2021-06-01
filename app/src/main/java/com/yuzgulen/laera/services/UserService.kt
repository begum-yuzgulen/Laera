package com.yuzgulen.laera.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("user/{uid}.json")
    fun getCurrentUser(@Path("uid") uid : String): Call<UserResponse>
}