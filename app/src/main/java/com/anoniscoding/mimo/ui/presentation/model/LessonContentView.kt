package com.anoniscoding.mimo.ui.presentation.model

import com.anoniscoding.mimo.domain.model.LessonContent

data class LessonContentView(
    val color: String,
    val text: String,
)

fun LessonContent.toLessonContentView() = LessonContentView(
    color = this.color,
    text = this.text
)