package com.yuzgulen.laera.services

import retrofit2.Call
import retrofit2.http.GET

interface TopicService {
    @GET("topics.json")
    fun getTopics(): Call<List<TopicResponse>>
}