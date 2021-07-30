package com.anoniscoding.mimo.ui.presentation.intent

import com.anoniscoding.mimo.ui.presentation.model.LessonView

sealed class LessonIntent {
    object OnNextEvent : LessonIntent()
    object OnRetryEvent : LessonIntent()
    data class OnInputEvent(val inputText: String) : LessonIntent()
}

data class LessonViewState(
    val currentLesson: LessonView,
    val isNextButtonEnabled: Boolean,
    val startTime: String
)

sealed class LessonViewEffect {
    object NavigateToDoneScreen : LessonViewEffect()
}

