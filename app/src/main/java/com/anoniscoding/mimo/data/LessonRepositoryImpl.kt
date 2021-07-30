package com.anoniscoding.mimo.data

import com.anoniscoding.mimo.data.contract.LessonCache
import com.anoniscoding.mimo.domain.contract.LessonRepository
import com.anoniscoding.mimo.domain.model.Lesson
import com.anoniscoding.mimo.remote.LessonService
import javax.inject.Inject

class LessonRepositoryImpl @Inject constructor(
    private val lessonCache: LessonCache,
    private val lessonService: LessonService
) : LessonRepository {

    override suspend fun getLessons(): List<Lesson> {
        return lessonService.getLessons().lessons
    }

    override suspend fun setLessonCompletionEvent(id: Int, startTime: String, endTime: String) {
        lessonCache.setLessonCompletionEvent(id, startTime, endTime)
    }
}