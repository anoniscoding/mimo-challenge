package com.anoniscoding.mimo.domain.model

data class Lesson(
    val id: Int,
    val content: List<LessonContent>,
    val input: LessonInput?
)