package com.android.myapplication.screens.common

import androidx.fragment.app.Fragment
import com.android.myapplication.common.composition.ActivityCompositionRoot
import com.android.myapplication.screens.common.activities.BaseActivity

open class BaseFragment: Fragment() {
protected  val compositionRoot:ActivityCompositionRoot get() = (requireActivity() as BaseActivity).compositionRoot
}