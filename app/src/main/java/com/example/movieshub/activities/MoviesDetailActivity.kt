package com.example.movieshub.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movieshub.data.model.response.Movie
import com.example.movieshub.databinding.ActivityMoviesDetailBinding
import com.example.movieshub.ui.moviedetail.adapter.CastRecyclerAdapter
import com.example.movieshub.ui.moviedetail.viewmodel.MovieDetailViewModel
import com.example.movieshub.util.SpacingItemDecoration
import com.example.movieshub.util.extension.getParcelableExtraCompat

class MoviesDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoviesDetailBinding
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val castAdapter = CastRecyclerAdapter()

    companion object {
        private const val MOVIE_TEXT = "movie"
        fun getIntent(context: Context, movie: Movie): Intent =
            Intent(context, MoviesDetailActivity::class.java).apply {
                putExtra(MOVIE_TEXT, movie)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        val movie = intent.getParcelableExtraCompat<Movie>(MOVIE_TEXT)
        movie?.id?.let { id ->
            viewModel.getCastDetail(id)
        }
        binding.movie = movie
        viewModel.castData.observe(this) { castData ->
            castData?.let {
                castAdapter.submitList(castData)
            }
        }
        binding.rvCast.apply {
            addItemDecoration(
                SpacingItemDecoration(
                    top = 10,
                    bottom = 10,
                    left = 10,
                    right = 10
                )
            )
            adapter = castAdapter
        }
    }

}