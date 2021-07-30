package com.anoniscoding.mimo.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.anoniscoding.mimo.cache.model.LessonCompletionEvent

@Dao
abstract class LessonCompletionEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract suspend fun insertLessonCompletionEvent(lessonCompletionEvent: LessonCompletionEvent)
}