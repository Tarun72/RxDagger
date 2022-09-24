package com.android.myapplication.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.myapplication.R
import com.android.myapplication.screens.common.toolbar.MyToolbar

class QuestionsDetailMVC constructor(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?

) {

    private lateinit var toolbar: MyToolbar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var txtQuestionBody: TextView
     var rootView:View

    private val listener = HashSet<ClickListener>()


    interface ClickListener{
        fun onBackClicked()
    }
    init {
        rootView = layoutInflater.inflate(R.layout.layout_question_details,viewGroup,false)
        initView()
    }

    private fun initView(){

        txtQuestionBody = findViewById(R.id.txt_question_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for(list in listener){
                list.onBackClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false

    }

    fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
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


    fun registerListener(listen: ClickListener) {
        listener.add(listen)
    }

    fun unregisterListener(listen: ClickListener) {
        listener.remove(listen)
    }

}