package com.android.myapplication.networking

import com.google.gson.annotations.SerializedName
import com.android.myapplication.questions.Question

class QuestionsListResponseSchema(@SerializedName("items") val questions: List<Question>)