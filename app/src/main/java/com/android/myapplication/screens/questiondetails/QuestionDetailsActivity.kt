package com.android.myapplication.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.myapplication.questions.QuestionDetailUseCase
import com.android.myapplication.screens.common.ScreenNavigator
import com.android.myapplication.screens.common.activities.BaseActivity
import com.android.myapplication.screens.common.dialogs.DialogNavigator
import kotlinx.coroutines.*

class QuestionDetailsActivity : BaseActivity(), QuestionsDatailMVC.ClickListener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String

    lateinit var questionMVC: QuestionsDatailMVC
    lateinit var questionDetailUseCase: QuestionDetailUseCase
    lateinit var dialogNavigator: DialogNavigator
    lateinit var screenNavigator: ScreenNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionMVC = compositionRoot.mvcFactory.toQuestionDetailActivity(null)
        setContentView(questionMVC.rootView)
        screenNavigator = compositionRoot.screenNavigator

        dialogNavigator = compositionRoot.dialogNavigator

        questionDetailUseCase = compositionRoot.questionDetailUseCase
        // retrieve question ID passed from outside
        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!
    }

    override fun onStart() {
        super.onStart()
        fetchQuestionDetails()
        questionMVC.registerListener(this)
    }

    override fun onStop() {
        super.onStop()

        coroutineScope.coroutineContext.cancelChildren()
        questionMVC.unRegisterListener(this)
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            questionMVC.showProgressIndication()
            try {
                val response = questionDetailUseCase.fetchQuestionDetails(questionId)
                when (response) {
                    is QuestionDetailUseCase.APIResult.SuccessResult -> {
                        questionMVC.bindData(response.question.body)

                    }
                    is QuestionDetailUseCase.APIResult.Failure -> {
                        onFetchFailed()
                    }
                }
            } finally {
                questionMVC.hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        dialogNavigator.showServerErrorDialog()
    }


    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }

    override fun onBackClicked() {
        screenNavigator.onActivityBackPress()
    }
}