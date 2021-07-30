package com.anoniscoding.mimo.ui.presentation.intent

import com.anoniscoding.mimo.ui.presentation.model.LessonView

sealed class LessonIntent {
    object OnNextEvent : LessonIntent()
    object OnRetryEvent : LessonIntent()
    data class OnInputEvent(val inputText: String) : LessonIntent()
}

