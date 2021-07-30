package com.anoniscoding.mimo.domain

import com.anoniscoding.mimo.domain.contract.LessonRepository
import com.anoniscoding.mimo.domain.usecase.GetLessonsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import com.nhaarman.mockitokotlin2.*


class GetLessonsUseCaseTest {
    private lateinit var useCase: GetLessonsUseCase

    private val repository = mock<LessonRepository>()

    @Before
    fun setup() {
        useCase = GetLessonsUseCase(repository)
    }

    @Test
    fun whenInvoke_verifyGetLessonsCalled() {
        runBlocking {
            useCase.invoke()
            verify(repository, times(1)).getLessons()
        }
    }
}