package com.anoniscoding.mimo.ui.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.anoniscoding.mimo.domain.usecase.GetLessonsUseCase
import com.anoniscoding.mimo.domain.usecase.SetLessonCompletionEventUseCase
import com.anoniscoding.mimo.ui.presentation.intent.LessonIntent
import com.anoniscoding.mimo.ui.presentation.intent.LessonViewEffect
import com.anoniscoding.mimo.ui.presentation.intent.LessonViewState
import com.anoniscoding.mimo.ui.presentation.model.LessonView
import com.anoniscoding.mimo.ui.presentation.model.toLessonView
import com.anoniscoding.mimo.ui.presentation.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val getLessonsUseCase: GetLessonsUseCase,
    private val setLessonCompletionEventUseCase: SetLessonCompletionEventUseCase
) : BaseViewModel<LessonViewState, LessonViewEffect>() {

    private val lessons: MutableList<LessonView> = mutableListOf()

    init {
        fetchLessons()
    }

    private fun fetchLessons() {
        launchRequest {
            lessons.addAll(getLessonsUseCase().map { it.toLessonView() })
            onNextEvent()
        }
    }

    fun setIntent(intent: LessonIntent) {
        when (intent) {
            is LessonIntent.OnNextEvent -> onNextEvent()
            is LessonIntent.OnInputEvent -> onInputEvent(intent.inputText)
            is LessonIntent.OnRetryEvent -> fetchLessons()
        }
    }

    private fun onNextEvent() {
        logCurrentLessonTime()

        val nextLessonIndex = lessons.indexOf(getViewState()?.currentLesson) + 1
        if (nextLessonIndex < lessons.size) {
            val newViewState = LessonViewState(
                currentLesson = lessons[nextLessonIndex],
                isNextButtonEnabled = !lessons[nextLessonIndex].hasInput(),
                startTime = System.currentTimeMillis().toString()
            )
            _dataStates.value = DataState.Success(newViewState)
        } else {
            _viewEffects.value = LessonViewEffect.NavigateToDoneScreen
        }
    }

    private fun logCurrentLessonTime() {
        getViewState()?.let {
            viewModelScope.launch {
                setLessonCompletionEventUseCase(
                    id = it.currentLesson.id,
                    startTime = it.startTime,
                    endTime = System.currentTimeMillis().toString()
                )
            }
        }
    }

    private fun onInputEvent(inputText: String) {
        val isNextButtonEnabled = getViewState()?.currentLesson?.getAnswer() == inputText
        _dataStates.value =
            DataState.Success(getViewState()?.copy(isNextButtonEnabled = isNextButtonEnabled))
    }
}