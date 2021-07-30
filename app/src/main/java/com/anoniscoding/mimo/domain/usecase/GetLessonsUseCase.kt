package com.anoniscoding.mimo.domain.usecase

import com.anoniscoding.mimo.domain.contract.LessonRepository
import com.anoniscoding.mimo.domain.model.Lesson
import javax.inject.Inject

class GetLessonsUseCase @Inject constructor(private val repository: LessonRepository) {
    suspend operator fun invoke(): List<Lesson> = repository.getLessons()
}