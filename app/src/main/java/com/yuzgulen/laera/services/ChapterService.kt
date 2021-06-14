package com.yuzgulen.laera.services

import com.yuzgulen.laera.models.Chapter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChapterService {
    @GET("chapters/{lessonId}.json")
    fun getLessonChapters(@Path("lessonId") lessonId : String): Call<List<ChapterResponse>>
}