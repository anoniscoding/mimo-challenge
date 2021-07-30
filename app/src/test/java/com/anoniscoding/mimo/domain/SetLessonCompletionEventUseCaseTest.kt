package com.anoniscoding.mimo.domain

import com.anoniscoding.mimo.domain.contract.LessonRepository
import com.anoniscoding.mimo.domain.usecase.SetLessonCompletionEventUseCase
import com.anoniscoding.mimo.factory.DataFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SetLessonCompletionEventUseCaseTest {
    private lateinit var useCase: SetLessonCompletionEventUseCase

    private val repository = mock<LessonRepository>()

    @Before
    fun setup() {
        useCase = SetLessonCompletionEventUseCase(repository)
    }

    @Test
    fun whenInvoke_verifySetLessonCompletionEventCalled() {
        runBlocking {
            val id = DataFactory.randomInt()
            val startTime = DataFactory.randomString()
            val endTime = DataFactory.randomString()
            useCase.invoke(id, startTime, endTime)
            verify(repository, times(1)).setLessonCompletionEvent(id, startTime, endTime)
        }
    }
}