package com.android.myapplication.networking

import com.google.gson.annotations.SerializedName
import com.android.myapplication.questions.QuestionWithBody

data class SingleQuestionResponseSchema(@SerializedName("items") val questions: List<QuestionWithBody>) {
    val question: QuestionWithBody get() = questions[0]
}