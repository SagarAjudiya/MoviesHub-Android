package com.example.movieshub.ui.home.view.fragments

import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.movieshub.activities.MoviesDetailActivity
import com.example.movieshub.databinding.FragmentHomeBinding
import com.example.movieshub.ui.home.repository.MovieRepository
import com.example.movieshub.ui.home.view.adapter.MovieAdapter
import com.example.movieshub.ui.home.view.adapter.MoviePagerAdapter
import com.example.movieshub.ui.home.viewmodel.HomeViewModel
import com.example.movieshub.util.NetworkStateViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<HomeViewModel> {
        HomeViewModel.Factory(MovieRepository())
    }
    private var popularMovieAdapter = MovieAdapter()
    private var inTheatresMovieAdapter = MovieAdapter()
    private var moviePagerAdapter = MoviePagerAdapter()
    private var listOfCategories = ArrayList<String>()
    private val networkStateViewModel by activityViewModels<NetworkStateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setUpViewModel()
        binding.viewPager2.adapter = moviePagerAdapter
        binding.txtvSeeAll.setOnClickListener {
            val action = HomeFragmentDirections
                .actionNavHomeToNavMovie()
                .setCategory("popular")
            findNavController().navigate(action)
        }
        binding.txtvSeeAllTheatre.setOnClickListener {
            val action = HomeFragmentDirections
                .actionNavHomeToNavMovie()
                .setCategory("now_playing")
            findNavController().navigate(action)
        }
        val itemDecoration = object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.apply {
                    left = 16
                    right = 16
                }
            }
        }

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(itemDecoration)
            popularMovieAdapter.itemOnClick = { movie ->
                startActivity(
                    MoviesDetailActivity.getIntent(
                        context,
                        movie
                    )
                )
            }
            adapter = popularMovieAdapter
        }

        binding.rvTheatre.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(itemDecoration)
            inTheatresMovieAdapter.itemOnClick = { movie ->
                startActivity(
                    MoviesDetailActivity.getIntent(
                        context,
                        movie
                    )
                )
            }
            adapter = inTheatresMovieAdapter
        }
        checkNetwork()
    }

    private fun setUpViewModel() {
        viewModel.apply {
            popularMovies.observe(viewLifecycleOwner) { movies ->
                movies?.results?.let { moviesList ->
                    popularMovieAdapter.submitList(moviesList)
                }
            }
            inTheatresMovie.observe(viewLifecycleOwner) { movies ->
                movies?.results?.let { moviesList ->
                    inTheatresMovieAdapter.submitList(moviesList)
                    moviePagerAdapter.submitList(moviesList)
                }
                binding.progressBar.visibility = View.GONE
            }
            categoryData.observe(viewLifecycleOwner) { categories ->
                listOfCategories.addAll(categories)
            }
        }
        networkStateViewModel.apply {
            networkState.observe(viewLifecycleOwner) { network ->
                manageNetworkStates(network)
            }
            getNetworkUpdate()
        }
    }

    private fun manageNetworkStates(network: Boolean) {
        if (network) {
            setViewsOnNetworkAvailable()
        } else {
            setViewsOnNetworkUnavailable()
        }
    }

    private fun checkNetwork() {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetwork
        if (netInfo == null) {
            binding.apply {
                progressBar.visibility = View.GONE
                viewGroup.visibility = View.GONE
                txtvNoInternet.visibility = View.VISIBLE
            }
        } else {
            setViewsOnNetworkAvailable()
        }
    }

    private fun setViewsOnNetworkUnavailable() {
        binding.apply {
            viewGroup.visibility = View.GONE
            txtvNoInternet.visibility = View.VISIBLE
        }
    }

    private fun setViewsOnNetworkAvailable() {
        binding.apply {
            viewGroup.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            txtvNoInternet.visibility = View.GONE
        }
        viewModel.apply {
            getPopularMovieList()
            getInTheatresMovies()
            getCategoriesDetail()
        }
    }

}