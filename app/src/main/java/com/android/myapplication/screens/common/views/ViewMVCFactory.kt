package com.android.myapplication.screens.common.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.myapplication.screens.questionslist.QuestionMVC
import com.android.myapplication.screens.questiondetails.QuestionsDatailMVC

class ViewMVCFactory (private val layoutInflater:LayoutInflater) {

    /**
     * this type of implementation is very for fragments
     * in fragment we use viewGroup container to attach fragment to parent
     */
    fun toQuestionDetailActivity(parent:ViewGroup?): QuestionsDatailMVC {
        return QuestionsDatailMVC(layoutInflater,parent)
    }

    fun toQuestionListActivity(parent:ViewGroup?): QuestionMVC {
        return QuestionMVC(layoutInflater,parent)
    }
}