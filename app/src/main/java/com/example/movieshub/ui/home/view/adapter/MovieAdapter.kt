package com.example.movieshub.ui.home.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.data.model.response.Movie
import com.example.movieshub.databinding.RecyclerMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private val listOfMovies = ArrayList<Movie>()
    var itemOnClick: ((Movie) -> Unit)? = null

    class MovieHolder(val binding: RecyclerMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val movieHolder = MovieHolder(
            RecyclerMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        movieHolder.binding.root.setOnClickListener {
            itemOnClick?.let { it(listOfMovies[movieHolder.adapterPosition]) }
        }
        return movieHolder
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(listOfMovies[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(listOfMovies: List<Movie>) {
        this.listOfMovies.addAll(listOfMovies)
        notifyDataSetChanged()
    }

}