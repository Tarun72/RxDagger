package com.android.myapplication.common

import com.android.myapplication.common.composition.PresentationCompositionRoot
import com.android.myapplication.screens.questiondetails.QuestionDetailsActivity
import com.android.myapplication.screens.questionslist.QuestionsListFragment

class Injector (private val compositionRoot: PresentationCompositionRoot){

    fun inject(fragment: QuestionsListFragment) {
        fragment.dialogNavigator = compositionRoot.dialogNavigator
        fragment.mvcFactory = compositionRoot.mvcFactory
        fragment.screenNavigator = compositionRoot.screenNavigator
        fragment.questionListUseCase = compositionRoot.questionListUseCase

    }

    fun inject(activity: QuestionDetailsActivity) {
        activity.questionDetailUseCase = compositionRoot.questionDetailUseCase
        activity.screenNavigator = compositionRoot.screenNavigator
        activity.dialogNavigator = compositionRoot.dialogNavigator
        activity.mvcFactory =compositionRoot.mvcFactory
    }
}