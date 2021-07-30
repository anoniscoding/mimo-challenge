package com.anoniscoding.mimo.domain.contract

import com.anoniscoding.mimo.domain.model.Lesson

interface LessonRepository {
    suspend fun getLessons(): List<Lesson>
    suspend fun setLessonCompletionEvent(id: Int, startTime: String, endTime: String)
}