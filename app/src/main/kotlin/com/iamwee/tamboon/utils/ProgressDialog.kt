package com.iamwee.tamboon.utils

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iamwee.tamboon.R

class ProgressDialog: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ProgressDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_progress_loading, container, false)
    }

    companion object {

        private const val TAG = "loadingDialogTag"

        fun show(fragmentManager: FragmentManager) {
            val progressBarDialog = ProgressDialog()

            try {
                progressBarDialog.show(fragmentManager, TAG)
            } catch (e: IllegalStateException) {
                fragmentManager.beginTransaction()
                    .add(progressBarDialog, TAG)
                    .commitAllowingStateLoss()
            }
        }

        fun dismiss(fragmentManager: FragmentManager) {
            val fragment = fragmentManager.findFragmentByTag(TAG) ?: return

            val progressBarDialog: ProgressDialog
            try {
                progressBarDialog = fragment as ProgressDialog
            } catch (e: ClassCastException) {
                throw ClassCastException("Cannot cast Fragment tag: $TAG to ProgressLoadingDialog")
            }

            try {
                progressBarDialog.dismiss()
            } catch (e: IllegalStateException) {
                progressBarDialog.dismissAllowingStateLoss()
            }
        }

    }
}
