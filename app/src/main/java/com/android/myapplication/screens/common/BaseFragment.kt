package com.android.myapplication.screens.common

import androidx.fragment.app.Fragment
import com.android.myapplication.common.Injector
import com.android.myapplication.common.composition.ActivityCompositionRoot
import com.android.myapplication.common.composition.PresentationCompositionRoot
import com.android.myapplication.screens.common.activities.BaseActivity

open class BaseFragment : Fragment() {
    private val compositionRoot: PresentationCompositionRoot
            by lazy {
                PresentationCompositionRoot(
                    (requireActivity() as BaseActivity)
                        .activityCompositionRoot
                )
            }

    protected  val injector get() = Injector(compositionRoot)
}