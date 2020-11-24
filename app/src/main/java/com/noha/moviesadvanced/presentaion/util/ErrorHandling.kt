package com.noha.moviesadvanced.presentaion.util

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.noha.moviesadvanced.R
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper

fun showErrorSnackBar(view: View, error: ResponseWrapper<Any>) {
    val context = view.context
    when (error) {
        is ResponseWrapper.GenericError -> {
            showErrorSnackBar(
                view,
                error.error?.message ?: context.getString(R.string.something_went_wrong)
            )
        }

        is ResponseWrapper.NetworkError -> showErrorSnackBar(
            view,
            context.getString(R.string.no_internet)
        )
        else -> showErrorSnackBar(
            view,
            context.getString(R.string.something_went_wrong)
        )
    }
}


fun showErrorSnackBar(view: View, msg: String) {
    if (msg.isNotBlank()) {
        Snackbar.make(
            view,
            msg,
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.RED).show()
    }
}