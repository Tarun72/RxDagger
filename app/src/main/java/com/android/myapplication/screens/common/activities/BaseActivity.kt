package com.android.myapplication.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.MyApplication
import com.android.myapplication.common.composition.AppCompositionRoot

open class BaseActivity : AppCompatActivity() {
    val compositionRoot:AppCompositionRoot get() = (application as MyApplication).appCompositionRoot
}