package com.android.myapplication.questions

import com.android.myapplication.Constants
import com.android.myapplication.networking.StackoverflowApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionListUseCase constructor( private  val stackoverflowApi: StackoverflowApi){

    sealed class APIResult {
        class SuccessResult(val questions: List<Question>) : APIResult()
        object Failure : APIResult()
    }


    suspend fun fetchQuestions() : APIResult{
       return withContext(Dispatchers.IO){
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext APIResult.SuccessResult(response.body()!!.questions)
                } else {
                    return@withContext APIResult.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext APIResult.Failure
                }else{
                    throw t
                }
            }
        }
    }

}