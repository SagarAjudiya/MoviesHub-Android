package com.example.movieshub.ui.home.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.data.model.response.Movie
import com.example.movieshub.databinding.MoviePagerBinding

class MoviePagerAdapter : RecyclerView.Adapter<MoviePagerAdapter.MovieViewHolder>() {

    private val listOfMovies = ArrayList<Movie>()

    class MovieViewHolder(private val binding: MoviePagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MoviePagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listOfMovies[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(listOfMovie: List<Movie>) {
        listOfMovies.addAll(listOfMovie)
        notifyDataSetChanged()
    }

}