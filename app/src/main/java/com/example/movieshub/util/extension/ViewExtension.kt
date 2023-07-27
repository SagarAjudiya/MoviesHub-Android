package com.example.movieshub.util.extension

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    @StringRes messageResId: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    actionListener: ((View) -> Unit)? = null
) {
    Snackbar.make(this, messageResId, duration).apply {
        actionText?.let { actionText ->
            setAction(actionText) { view ->
                actionListener?.invoke(view)
            }
        }
        show()
    }
}