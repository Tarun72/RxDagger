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
    private val stackOverflowError get() = appCompositionRoot.stackoverflowApi
    private val fragmentManager get() = activity.supportFragmentManager
    val dialogNavigator get() = DialogNavigator(fragmentManager)

    val questionDetailUseCase get() = QuestionDetailUseCase(stackOverflowError)
    val questionListUseCase get() = QuestionListUseCase(stackOverflowError)

    private val layoutInflater get() = LayoutInflater.from(activity)
     val mvcFactory:ViewMVCFactory get() = ViewMVCFactory(layoutInflater)

}