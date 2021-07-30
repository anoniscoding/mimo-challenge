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
        }
    }

}