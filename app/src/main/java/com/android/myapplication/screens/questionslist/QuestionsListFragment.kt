package com.android.myapplication.screens.questionslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.myapplication.questions.Question
import com.android.myapplication.questions.QuestionListUseCase
import com.android.myapplication.screens.common.BaseFragment
import com.android.myapplication.screens.common.ScreenNavigator
import com.android.myapplication.screens.common.dialogs.DialogNavigator
import com.android.myapplication.screens.common.views.ViewMVCFactory
import com.android.myapplication.screens.rxjava.RxjavaActivity
import kotlinx.coroutines.*

class QuestionsListFragment : BaseFragment(), QuestionMVC.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    lateinit var viewQuestionMVC: QuestionMVC
    lateinit var questionListUseCase: QuestionListUseCase
    lateinit var dialogNavigator: DialogNavigator
    lateinit var screenNavigator: ScreenNavigator
    lateinit var mvcFactory: ViewMVCFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
        // improved version of questionListUseCase
        // Law of demeter, we unnessary using retrofit and StackOverflow API instances
        // activity need not to know about the StackOverflow API and Retrofit instance
        // it only require fetch question list ..... Talk to immediate friends
        /***********************************************************************/
        // we ar using  (application as MyApplication).appCompositionRoot again and again
        // so we need to move it upper level.... one level up
        // so we are creating base activity here
//        questionListUseCase = (application as MyApplication).appCompositionRoot.questionListUseCase

//        injected in framework way
  /*      questionListUseCase = compositionRoot.questionListUseCase
        screenNavigator = compositionRoot.screenNavigator
        dialogNavigator = compositionRoot.dialogNavigator
*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewQuestionMVC = mvcFactory.toQuestionListActivity(container)
        return viewQuestionMVC.rootView
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
        val intent = Intent(activity, RxjavaActivity::class.java)
        startActivity(intent)
    }
}