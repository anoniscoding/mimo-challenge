package com.anoniscoding.mimo.factory

import com.anoniscoding.mimo.domain.model.Lesson
import com.anoniscoding.mimo.domain.model.LessonContent
import com.anoniscoding.mimo.domain.model.LessonInput

object LessonFactory {

    fun makeLessonList(count: Int = 2): List<Lesson> {
        val list = mutableListOf<Lesson>()
        repeat(2) {
            list.add(makeLesson())
        }
        return list
    }

    private fun makeLesson(): Lesson {
        val content = makeLessonContent()
        return Lesson(
            DataFactory.randomInt(), listOf(content), makeLessonInput(0, content.text.lastIndex -1)
        )
    }

    private fun makeLessonContent(): LessonContent {
        return LessonContent(
            DataFactory.randomString(), DataFactory.randomString()
        )
    }
    private fun makeLessonInput(startIndex: Int, endIndex: Int): LessonInput {
        return LessonInput(startIndex, endIndex)
    }
}