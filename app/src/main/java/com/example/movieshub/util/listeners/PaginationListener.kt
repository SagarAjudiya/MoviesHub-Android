package com.example.movieshub.util.listeners

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(private val layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (isLoading || isLastPage) return

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            loadMoreItems()
        }
    }

    abstract val isLastPage: Boolean

    abstract val isLoading: Boolean

    abstract fun loadMoreItems()

//    companion object { // if want to implement PAGE SIZE form here   && totalItemCount >= PAGE_SIZE
//        const val PAGE_SIZE = 20
//    }
}