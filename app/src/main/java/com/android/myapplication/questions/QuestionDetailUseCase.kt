package com.android.myapplication.questions

import com.android.myapplication.Constants
import com.android.myapplication.networking.StackoverflowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class QuestionDetailUseCase constructor( private val retrofit: Retrofit){

    val stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    sealed class APIResult {
        class SuccessResult(val question: QuestionWithBody) : APIResult()
        object Failure : APIResult()
    }


    suspend fun fetchQuestionDetails(questionId: String?): APIResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.questionDetails(questionId)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext APIResult.SuccessResult(response.body()!!.question)
                } else {
                    return@withContext APIResult.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext APIResult.Failure
                } else throw t
            }
        }
    }
}