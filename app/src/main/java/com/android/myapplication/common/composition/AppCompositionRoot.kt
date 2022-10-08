package com.android.myapplication.common.composition

import androidx.annotation.UiThread
import com.android.myapplication.Constants
import com.android.myapplication.networking.StackoverflowApi
import com.android.myapplication.questions.QuestionDetailUseCase
import com.android.myapplication.questions.QuestionListUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * this is application level (app) service composing class (creating QuestionDetailUseCase and QuestionListUseCase)
 * root(it is present at the root of all dependency )
 * leaves QuestionDetailUseCase and QuestionListUseCase and root of all dependency tree is AppCompositionRoot
 * 
 * */
@UiThread
class AppCompositionRoot {
    // init retrofit
    private val retrofit by lazy {  Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
     val stackoverflowApi: StackoverflowApi by lazy {
        retrofit.create(StackoverflowApi::class.java)
    }


}