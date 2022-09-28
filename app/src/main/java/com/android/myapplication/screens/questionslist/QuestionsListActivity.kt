package com.android.myapplication.screens.questionslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.Constants
import com.android.myapplication.screens.questiondetails.QuestionDetailsActivity
import com.android.myapplication.screens.common.dialogs.ServerErrorDialogFragment
import com.android.myapplication.networking.StackoverflowApi
import com.android.myapplication.questions.Question
import com.android.myapplication.questions.QuestionListUseCase
import com.android.myapplication.screens.rxjava.RxjavaActivity
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionsListActivity : AppCompatActivity(), QuestionMVC.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    lateinit var viewQuestionMVC: QuestionMVC
    lateinit var questionListUseCase: QuestionListUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewQuestionMVC = QuestionMVC(LayoutInflater.from(this), null)
questionListUseCase = QuestionListUseCase()
        setContentView(viewQuestionMVC.rootView)

    }

    override fun onStart() {
        super.onStart()
        viewQuestionMVC.registerListener(this)
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        viewQuestionMVC.unRegisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            viewQuestionMVC.showProgressIndication()
            try {
                val response = questionListUseCase.fetchQuestions()
                when (response) {
                    is QuestionListUseCase.APIResult.SuccessResult -> {
                        viewQuestionMVC.bindQuestions(response.questions)
                        isDataLoaded = true
                    }
                    is QuestionListUseCase.APIResult.Failure -> {
                        onFetchFailed()
                    }
                }
            } finally {
                viewQuestionMVC.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }


    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(clickedQuestion: Question) {
        QuestionDetailsActivity.start(this, clickedQuestion.id)

    }

    override fun onRxModuleClick() {
        val intent = Intent(this, RxjavaActivity::class.java)
        startActivity(intent)
    }
}