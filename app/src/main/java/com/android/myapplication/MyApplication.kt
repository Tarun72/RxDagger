package com.android.myapplication

import android.app.Application
import com.android.myapplication.common.composition.AppCompositionRoot
import com.android.myapplication.networking.StackoverflowApi
import com.android.myapplication.questions.QuestionDetailUseCase
import com.android.myapplication.questions.QuestionListUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    // we can not keep retrofit instance in application class
    // it will create mess as the application grows
    // we have created another class name #AppCompositionRoot
    lateinit var appCompositionRoot: AppCompositionRoot
    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot()
        super.onCreate()
    }

}