package com.android.myapplication.screens.questionslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.myapplication.R
import com.android.myapplication.questions.Question
import com.android.myapplication.screens.common.views.BasicMVCView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class QuestionMVC(layoutInflater: LayoutInflater,viewGroup: ViewGroup?) :
    BasicMVCView<QuestionMVC.Listener>(layoutInflater,viewGroup,R.layout.layout_questions_list) {

    interface Listener{
        fun onRefreshClicked()
        fun onQuestionClicked(clickedQuestion: Question)
        fun onRxModuleClick()
    }
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var rxEntryButton:FloatingActionButton


    override fun initView(){
        // init pull-down-to-refresh

        // init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            for (lisetnerReference in listeners){
                lisetnerReference.onRefreshClicked()
            }
        }

        // init recycler view
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        questionsAdapter = QuestionsAdapter { clickedQuestion ->
            for (mlistern in listeners){
                mlistern.onQuestionClicked(clickedQuestion)
            }
        }
        recyclerView.adapter = questionsAdapter
        rxEntryButton = findViewById(R.id.rxEntry)
        rxEntryButton.setOnClickListener {
            for (mlistern in listeners){
                mlistern.onRxModuleClick()
            }
        }

    }

     fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

     fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }


    class QuestionsAdapter(
        private val onQuestionClickListener: (Question) -> Unit
    ) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

        private var questionsList: List<Question> = ArrayList(0)

        inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.txt_title)
        }

        fun bindData(questions: List<Question>) {
            questionsList = ArrayList(questions)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_question_list_item, parent, false)
            return QuestionViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.title.text = questionsList[position].title
            holder.itemView.setOnClickListener {
                onQuestionClickListener.invoke(questionsList[position])
            }
        }

        override fun getItemCount(): Int {
            return questionsList.size
        }

    }
}