package com.anoniscoding.mimo.cache.service

import com.anoniscoding.mimo.cache.db.AppDatabase
import com.anoniscoding.mimo.cache.model.LessonCompletionEvent
import com.anoniscoding.mimo.data.contract.LessonCache
import javax.inject.Inject

class LessonCacheImpl @Inject constructor(
    private val database: AppDatabase,
) : LessonCache {
    override suspend fun setLessonCompletionEvent(id: Int, startTime: String, endTime: String) {
        val event = LessonCompletionEvent(
            id = id,
            startTime = startTime,
            endTime = endTime
        )
        database.lessonCompletionEventDao().insertLessonCompletionEvent(event)
    }
}