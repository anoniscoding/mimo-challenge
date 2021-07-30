package com.anoniscoding.mimo.domain.usecase

import com.anoniscoding.mimo.domain.contract.LessonRepository
import javax.inject.Inject

class SetLessonCompletionEventUseCase @Inject constructor(private val repository: LessonRepository) {
    suspend operator fun invoke(id: Int, startTime: String, endTime: String) {
        repository.setLessonCompletionEvent(id, startTime, endTime)
    }
}