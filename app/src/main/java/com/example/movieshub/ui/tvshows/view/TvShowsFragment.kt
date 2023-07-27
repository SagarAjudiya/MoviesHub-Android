package com.example.movieshub.ui.tvshows.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieshub.R
import com.example.movieshub.databinding.FragmentTvShowsBinding
import com.example.movieshub.ui.movies.adapter.MovieAdapter
import com.example.movieshub.ui.tvshows.repository.TVShowsRepository
import com.example.movieshub.ui.tvshows.viewmodel.TVShowsVM
import com.example.movieshub.util.listeners.PaginationListener

class TvShowsFragment : Fragment() {

    private lateinit var binding: FragmentTvShowsBinding
    private val viewModel by navGraphViewModels<TVShowsVM>(
        navGraphId = R.id.mobile_navigation,
        factoryProducer = { TVShowsVM.Factory(TVShowsRepository()) }
    )
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setUpRecyclerView()
        setUpViewModel()
        viewModel.getTVShows()
    }

    private fun setUpViewModel() {
        viewModel.apply {
            showsLiveData.observe(viewLifecycleOwner) {
                movieAdapter.addList(it)
            }
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) movieAdapter.showLoading() else movieAdapter.hideLoading()
            }
            isFailure.observe(viewLifecycleOwner) { failureMessage ->
                if (failureMessage.isNotEmpty()) {
                    Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvShows.apply {
            adapter = movieAdapter
            val gridLayoutManager = GridLayoutManager(context, 3)
            layoutManager = gridLayoutManager
            addOnScrollListener(object : PaginationListener(gridLayoutManager) {
                override val isLastPage: Boolean
                    get() = viewModel.isLastPage
                override val isLoading: Boolean
                    get() {
                        return viewModel.isLoading.value ?: false
                    }

                override fun loadMoreItems() {
                    viewModel.getTVShows()
                }
            })
        }
    }
}