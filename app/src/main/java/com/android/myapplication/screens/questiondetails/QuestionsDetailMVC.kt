package com.android.myapplication.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.myapplication.R
import com.android.myapplication.screens.common.toolbar.MyToolbar
import com.android.myapplication.screens.common.views.BasicMVCView

class QuestionsDetailMVC constructor(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?
): BasicMVCView<QuestionsDetailMVC.ClickListener>(layoutInflater,viewGroup  ,R.layout.layout_question_details) {

    private lateinit var toolbar: MyToolbar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var txtQuestionBody: TextView

    interface ClickListener{
        fun onBackClicked()
    }

    override fun initView(){

        txtQuestionBody = findViewById(R.id.txt_question_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for(list in listeners){
                list.onBackClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false

    }


     fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

     fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }

        fun bindData(questionBody:String){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtQuestionBody.text = Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                txtQuestionBody.text = Html.fromHtml(questionBody)
            }
        }

}