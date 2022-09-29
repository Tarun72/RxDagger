package com.android.myapplication.screens.common.dialogs

import androidx.fragment.app.FragmentManager

class DialogNavigator(private val fragmentManager: FragmentManager) {

    fun showServerErrorDialog(){
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}