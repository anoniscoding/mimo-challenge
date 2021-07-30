package com.anoniscoding.mimo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anoniscoding.mimo.domain.contract.LessonRepository
import com.anoniscoding.mimo.domain.usecase.GetLessonsUseCase
import com.anoniscoding.mimo.domain.usecase.SetLessonCompletionEventUseCase
import com.anoniscoding.mimo.util.CoroutineRule
import com.anoniscoding.mimo.factory.DataFactory
import com.anoniscoding.mimo.factory.LessonFactory
import com.anoniscoding.mimo.util.getValueForTest
import com.anoniscoding.mimo.ui.presentation.intent.LessonIntent
import com.anoniscoding.mimo.ui.presentation.intent.LessonViewEffect
import com.anoniscoding.mimo.ui.presentation.model.toLessonView
import com.anoniscoding.mimo.ui.presentation.viewmodel.LessonViewModel
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LessonViewModelTest {
    @get:Rule
    val coroutineScope = CoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = mock<LessonRepository>()
    private val getLessonsUseCase = GetLessonsUseCase(repository)
    private val setLessonCompletionEventUseCase = SetLessonCompletionEventUseCase(repository)

    private lateinit var viewModel: LessonViewModel

    @Before
    fun setup() {
        viewModel = LessonViewModel(getLessonsUseCase, setLessonCompletionEventUseCase)
    }

    @Test
    fun whenRetryEvent_succeeds() {
        runBlockingTest {
            val lessons = LessonFactory.makeLessonList()
            whenever(getLessonsUseCase.invoke()).thenReturn(lessons)
            viewModel.setIntent(LessonIntent.OnRetryEvent)
            Truth.assertThat(viewModel.dataStates.getValueForTest()?.toData()?.currentLesson)
                .isEqualTo(lessons.first().toLessonView())
        }
    }

    @Test
    fun whenRetryEvent_fails() {
        runBlockingTest {
            val exception = RuntimeException()
            given(getLessonsUseCase.invoke()).willThrow(exception)
            viewModel.setIntent(LessonIntent.OnRetryEvent)
            Truth.assertThat(viewModel.dataStates.getValueForTest()?.toError()).isEqualTo(exception)
        }
    }

    @Test
    fun whenNextEvent_showNextLesson() {
        runBlockingTest {
            val lessons = LessonFactory.makeLessonList(2)
            whenever(getLessonsUseCase.invoke()).thenReturn(lessons)
            viewModel.setIntent(LessonIntent.OnRetryEvent)
            viewModel.setIntent(LessonIntent.OnNextEvent)
            Truth.assertThat(viewModel.dataStates.getValueForTest()?.toData()?.currentLesson)
                .isEqualTo(lessons.last().toLessonView())
        }
    }

    @Test
    fun whenNextEvent_navigateToDoneScreen() {
        runBlockingTest {
            val lessons = LessonFactory.makeLessonList(2)
            whenever(getLessonsUseCase.invoke()).thenReturn(lessons)
            viewModel.setIntent(LessonIntent.OnRetryEvent)
            viewModel.setIntent(LessonIntent.OnNextEvent)
            viewModel.setIntent(LessonIntent.OnNextEvent)
            Truth.assertThat(viewModel.viewEffects.getValueForTest())
                .isEqualTo(LessonViewEffect.NavigateToDoneScreen)
        }
    }

    @Test
    fun whenInputEvent_EnableNextButton() {
        runBlockingTest {
            val lessons = LessonFactory.makeLessonList(2)
            val answer = lessons.first().toLessonView().getAnswer()

            whenever(getLessonsUseCase.invoke()).thenReturn(lessons)
            viewModel.setIntent(LessonIntent.OnRetryEvent)
            viewModel.setIntent(LessonIntent.OnInputEvent(answer))
            Truth.assertThat(viewModel.dataStates.getValueForTest()?.toData()?.isNextButtonEnabled)
                .isEqualTo(true)
        }
    }

    @Test
    fun whenInputEvent_DisableNextButton() {
        runBlockingTest {
            val lessons = LessonFactory.makeLessonList(2)
            whenever(getLessonsUseCase.invoke()).thenReturn(lessons)
            viewModel.setIntent(LessonIntent.OnRetryEvent)
            viewModel.setIntent(LessonIntent.OnInputEvent(DataFactory.randomString()))
            Truth.assertThat(viewModel.dataStates.getValueForTest()?.toData()?.isNextButtonEnabled)
                .isEqualTo(false)
        }
    }
}