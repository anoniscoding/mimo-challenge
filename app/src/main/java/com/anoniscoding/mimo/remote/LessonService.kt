package com.anoniscoding.mimo.remote

import com.anoniscoding.mimo.remote.model.LessonResponse
import retrofit2.http.GET

interface LessonService {
    @GET("api/lessons")
    suspend fun getLessons(): LessonResponse
}