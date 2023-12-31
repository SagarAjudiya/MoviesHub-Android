package com.example.movieshub.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    private val top: Int,
    private val bottom: Int,
    private val left: Int,
    private val right: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            top = this@SpacingItemDecoration.top
            bottom = this@SpacingItemDecoration.bottom
            left = this@SpacingItemDecoration.left
            right = this@SpacingItemDecoration.right
        }
    }

}