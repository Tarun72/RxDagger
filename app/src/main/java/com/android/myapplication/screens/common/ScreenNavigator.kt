package com.android.myapplication.screens.common

import android.app.Activity
import com.android.myapplication.screens.questiondetails.QuestionDetailsActivity

class ScreenNavigator constructor(private val activity:Activity) {

    fun toDetailsActivity(questionId:String){
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun onActivityBackPress(){
        activity.onBackPressed()
    }
}