package com.android.myapplication.common.composition

import com.android.myapplication.questions.QuestionDetailUseCase
import com.android.myapplication.questions.QuestionListUseCase
import com.android.myapplication.screens.common.ScreenNavigator
import com.android.myapplication.screens.common.dialogs.DialogNavigator
import com.android.myapplication.screens.common.views.ViewMVCFactory

class PresentationCompositionRoot(private val compositionRoot: ActivityCompositionRoot) {

    val screenNavigator:ScreenNavigator get() = compositionRoot.screenNavigator
    val dialogNavigator get() = DialogNavigator(compositionRoot.fragmentManager)
    val questionDetailUseCase get() = QuestionDetailUseCase(compositionRoot.stackOverflowError)
    val questionListUseCase get() = QuestionListUseCase(compositionRoot.stackOverflowError)
    val mvcFactory: ViewMVCFactory get() = ViewMVCFactory(compositionRoot.layoutInflater)

}