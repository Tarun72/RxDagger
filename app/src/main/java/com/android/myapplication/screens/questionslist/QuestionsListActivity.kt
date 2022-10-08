package com.android.myapplication.screens.questionslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.MyApplication
import com.android.myapplication.screens.questiondetails.QuestionDetailsActivity
import com.android.myapplication.screens.common.dialogs.ServerErrorDialogFragment
import com.android.myapplication.questions.Question
import com.android.myapplication.questions.QuestionListUseCase
import com.android.myapplication.screens.common.ScreenNavigator
import com.android.myapplication.screens.common.activities.BaseActivity
import com.android.myapplication.screens.common.dialogs.DialogNavigator
import com.android.myapplication.screens.questiondetails.QuestionMVC
import com.android.myapplication.screens.rxjava.RxjavaActivity
import kotlinx.coroutines.*
class QuestionsListActivity : BaseActivity(), QuestionMVC.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    lateinit var viewQuestionMVC: QuestionMVC
    lateinit var questionListUseCase: QuestionListUseCase
    lateinit var dialogNavigator: DialogNavigator
    lateinit var screenNavigator: ScreenNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewQuestionMVC = QuestionMVC(LayoutInflater.from(this), null)
        setContentView(viewQuestionMVC.rootView)
        // improved version of questionListUseCase
        // Law of demeter, we unnessary using retrofit and StackOverflow API instances
        // activity need not to know about the StackOverflow API and Retrofit instance
        // it only require fetch question list ..... Talk to immediate friends
        /***********************************************************************/
       // we ar using  (application as MyApplication).appCompositionRoot again and again
        // so we need to move it upper level.... one level up
        // so we are creating base activity here
//        questionListUseCase = (application as MyApplication).appCompositionRoot.questionListUseCase

        questionListUseCase = compositionRoot.questionListUseCase
        screenNavigator = compositionRoot.screenNavigator
        dialogNavigator = compositionRoot.dialogNavigator

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
        dialogNavigator.showServerErrorDialog()
    }


    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(clickedQuestion: Question) {
        screenNavigator.toDetailsActivity(clickedQuestion.id)
    }

    override fun onRxModuleClick() {
        val intent = Intent(this, RxjavaActivity::class.java)
        startActivity(intent)
    }
}