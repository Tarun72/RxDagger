package com.android.myapplication.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.screens.common.dialogs.ServerErrorDialogFragment
import com.android.myapplication.questions.QuestionDetailUseCase
import kotlinx.coroutines.*

class QuestionDetailsActivity : AppCompatActivity(), QuestionsDetailMVC.ClickListener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String

    lateinit var questionMVC: QuestionsDetailMVC
    lateinit var questionDetailUseCase: QuestionDetailUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionMVC = QuestionsDetailMVC(LayoutInflater.from(this), null)
        setContentView(questionMVC.rootView)


        questionDetailUseCase = QuestionDetailUseCase()
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
                when(response){
                    is QuestionDetailUseCase.APIResult.SuccessResult ->{
                        questionMVC.bindData(response.question.body)

                    } is QuestionDetailUseCase.APIResult.Failure ->{
                        onFetchFailed()
                    }
                }
            } finally {
                questionMVC.hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
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
        onBackPressed()
    }
}