package com.android.myapplication.screens.questionslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.android.myapplication.R
import com.android.myapplication.screens.common.activities.BaseActivity
class QuestionsListActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container_activity)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().add(R.id.frameLayout,QuestionsListFragment()).commit()
        }
    }

}