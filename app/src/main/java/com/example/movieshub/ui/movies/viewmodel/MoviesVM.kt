package com.example.movieshub.ui.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieshub.data.model.response.Movie
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.ui.movies.respository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesVM(private val repository: MoviesRepository) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<ArrayList<Movie>>()
    val moviesLiveData: LiveData<ArrayList<Movie>> = _moviesLiveData

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    val isLastPage: Boolean
        get() = false
    private var currentPage: Int = 0

    fun getMovies(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            currentPage++
            val movieResult= repository.getMovies(category = category, currentPage = currentPage)
            withContext(Dispatchers.Main){
                _isLoading.postValue(false)
                when {
                    movieResult.isFailure -> {
                        postFailureIfPossible(movieResult)
                    }

                    movieResult.isSuccess -> {
                        movieResult.getOrNull()?.results?.let {
                            _moviesLiveData.postValue(it)
                            return@withContext
                        }
                        postFailureIfPossible(movieResult)
                    }
                }
            }
        }
    }

    private fun postFailureIfPossible(movieResult: Result<MovieResponse>) {
        val message = movieResult.exceptionOrNull()?.localizedMessage ?: ""
        if (message.isNotEmpty()) {
            _isFailure.postValue(message)
            // TODO: Show Alert
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: MoviesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesVM(repository) as T
        }
    }

}