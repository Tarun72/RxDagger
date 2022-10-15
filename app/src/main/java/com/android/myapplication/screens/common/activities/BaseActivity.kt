package com.android.myapplication.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.MyApplication
import com.android.myapplication.common.Injector
import com.android.myapplication.common.composition.ActivityCompositionRoot
import com.android.myapplication.common.composition.AppCompositionRoot
import com.android.myapplication.common.composition.PresentationCompositionRoot

open class BaseActivity : AppCompatActivity() {
    val activityCompositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot(this, (application as MyApplication).appCompositionRoot)

    }
   private val compositionRoot: PresentationCompositionRoot by lazy {
       PresentationCompositionRoot(activityCompositionRoot)
   }

    protected  val injector get() = Injector(compositionRoot)

}