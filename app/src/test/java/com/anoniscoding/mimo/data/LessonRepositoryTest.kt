package com.anoniscoding.mimo.data

import com.anoniscoding.mimo.data.contract.LessonCache
import com.anoniscoding.mimo.factory.DataFactory
import com.anoniscoding.mimo.factory.LessonFactory
import com.anoniscoding.mimo.remote.LessonService
import com.anoniscoding.mimo.remote.model.LessonResponse
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class LessonRepositoryTest {
    private lateinit var repository: LessonRepositoryImpl

    private val lessonService = mock<LessonService>()
    private val lessonCache = mock<LessonCache>()

    @Before
    fun setup() {
        repository = LessonRepositoryImpl(lessonCache, lessonService)
    }

    @Test
    fun whenGetLessons_succeeds() {
        runBlockingTest {
            val lessonResponse = LessonResponse(lessons = LessonFactory.makeLessonList())
            whenever(lessonService.getLessons()).thenReturn(lessonResponse)
            val result = repository.getLessons()
            Truth.assertThat(lessonResponse.lessons).isEqualTo(result)
        }
    }

    @Test(expected = Exception::class)
    fun whenGetLessons_fails() {
        runBlockingTest {
            given(lessonService.getLessons()).willThrow(RuntimeException())
            repository.getLessons()
        }
    }

    @Test
    fun whenSetLessonCompletionEvent_cacheSetLessonCompletionEventGetCalled() {
        runBlockingTest {
            val id = DataFactory.randomInt()
            val startTime = DataFactory.randomString()
            val endTime = DataFactory.randomString()
            repository.setLessonCompletionEvent(id, startTime, endTime)
            verify(lessonCache, times(1)).setLessonCompletionEvent(id, startTime, endTime)
        }
    }
}