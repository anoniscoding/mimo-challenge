package com.anoniscoding.mimo.ui.presentation.model

import com.anoniscoding.mimo.domain.model.LessonInput

data class LessonInputView(
    val startIndex: Int,
    val endIndex: Int
)


fun LessonInput.toLessonInputView() = LessonInputView(
    startIndex = this.startIndex,
    endIndex = this.endIndex
)