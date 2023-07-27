package com.example.movieshub.ui.movies.respository

import android.util.Log
import com.example.movieshub.api.RetrofitClient
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.util.base.BaseRepository

class MoviesRepository: BaseRepository() {
    suspend fun getMovies(category: String, currentPage: Int): Result<MovieResponse> {
        return try {
            val response =
                RetrofitClient.service.getMoviesList(category = category, language = "en-US", page = currentPage)
            handleResponse(response)
        } catch (e: Exception) {
            Log.d("TAG", "getMovies: ${e.localizedMessage}")
            Result.failure(Throwable(e.localizedMessage))
        }
    }
}