package com.anoniscoding.mimo.data.contract

interface LessonCache {
    suspend fun setLessonCompletionEvent(id: Int, startTime: String, endTime: String)
}