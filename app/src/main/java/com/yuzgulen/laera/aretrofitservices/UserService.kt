package com.yuzgulen.laera.aretrofitservices

import com.yuzgulen.laera.domain.models.ProgressResponse
import com.yuzgulen.laera.domain.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("user/{uid}.json")
    fun getCurrentUser(@Path("uid") uid : String): Call<UserResponse>

    @GET("progress/{uid}.json")
    fun getProgressUser(@Path("uid") uid : String): Call<ProgressResponse>

    @PUT("progress/{uid}/{id}.json")
    fun updateUserProgress(@Path("uid") uid : String, @Path("id") id : String, @Body progress: ProgressResponse): Call<List<ProgressResponse>>
}