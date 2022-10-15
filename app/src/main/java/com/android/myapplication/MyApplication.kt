package com.android.myapplication

import android.app.Application
import com.android.myapplication.common.composition.AppCompositionRoot

class MyApplication : Application() {
    // we can not keep retrofit instance in application class
    // it will create mess as the application grows
    // we have created another class name #AppCompositionRoot
    lateinit var appCompositionRoot: AppCompositionRoot
    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot(this)
        super.onCreate()
    }

}