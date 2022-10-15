package com.android.myapplication.common.composition

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.questions.QuestionDetailUseCase
import com.android.myapplication.questions.QuestionListUseCase
import com.android.myapplication.screens.common.ScreenNavigator
import com.android.myapplication.screens.common.dialogs.DialogNavigator
import com.android.myapplication.screens.common.views.ViewMVCFactory

// boot strap dependencies
class ActivityCompositionRoot (private val activity : AppCompatActivity
,private val appCompositionRoot: AppCompositionRoot) {

    val screenNavigator by lazy {
        ScreenNavigator(activity)
    }
     val stackOverflowError get() = appCompositionRoot.stackoverflowApi
     val fragmentManager get() = activity.supportFragmentManager

     val layoutInflater:LayoutInflater get() = LayoutInflater.from(activity)

    val application get() = appCompositionRoot.application

}