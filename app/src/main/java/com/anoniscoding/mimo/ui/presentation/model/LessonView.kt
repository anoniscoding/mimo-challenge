package com.anoniscoding.mimo.ui.presentation.model

import android.text.Spanned
import com.anoniscoding.mimo.domain.model.Lesson
import com.anoniscoding.mimo.util.toHtmlSpanned

data class LessonView(
    val id: Int,
    val content: List<LessonContentView>,
    val input: LessonInputView?
) {
    fun hasInput() = input != null

    fun getAnswer(): String {
        var fullContentText = ""
        content.forEach { fullContentText += it.text }
        return fullContentText.substring(input?.startIndex ?: 0, input?.endIndex ?: 0)
    }

    fun getAnswerColor(): String {
        val answer = getAnswer()
        return content.find { it.text.contains(answer) }?.color ?: ""
    }

    fun getFullContentAsHtmlSpanned(): Spanned {
        var fullContentHtmlText = ""
        content.forEach { fullContentHtmlText += "<font color=\"${it.color}\">${it.text}</font>" }
        return fullContentHtmlText.toHtmlSpanned()
    }
}

fun Lesson.toLessonView() = LessonView(
    id = this.id,
    content = this.content.map { it.toLessonContentView() },
    input = this.input?.toLessonInputView()
)