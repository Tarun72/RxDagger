package com.android.myapplication.screens.common.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

abstract class BasicMVCView<LISTENER_TYPE>(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?,
    @LayoutRes layoutId: Int
) {

     var rootView: View
    protected val context: Context get() = rootView.context
    abstract fun initView()
    val listeners = HashSet<LISTENER_TYPE>()

    init {
        rootView = layoutInflater.inflate(layoutId, viewGroup, false)
        initView()
    }


    fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }


    fun registerListener(listener: LISTENER_TYPE) {
        listeners.add(listener)
    }


    fun unRegisterListener(listener: LISTENER_TYPE) {
        listeners.remove(listener)
    }
}