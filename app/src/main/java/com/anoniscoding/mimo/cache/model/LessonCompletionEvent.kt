package com.anoniscoding.mimo.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LessonCompletionEventConstants.TABLE_NAME)
data class LessonCompletionEvent(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val startTime: String,
    val endTime: String,
)

object LessonCompletionEventConstants {
    const val TABLE_NAME = "lesson_completion_event"
}