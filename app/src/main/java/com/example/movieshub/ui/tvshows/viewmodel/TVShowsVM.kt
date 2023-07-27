package com.example.movieshub.ui.tvshows.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieshub.data.model.response.Movie
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.ui.tvshows.repository.TVShowsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TVShowsVM(private val repository: TVShowsRepository) : ViewModel() {

    private val _showsLiveData = MutableLiveData<ArrayList<Movie>>()
    val showsLiveData: LiveData<ArrayList<Movie>> = _showsLiveData

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    val isLastPage: Boolean
        get() = false
    private var currentPage: Int = 0

    fun getTVShows() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            currentPage++
            val movieResult = repository.getTVShows(currentPage = currentPage)
            withContext(Dispatchers.Main) {
                _isLoading.postValue(false)
                when {
                    movieResult.isFailure -> {
                        postFailureIfPossible(movieResult)
                    }

                    movieResult.isSuccess -> {
                        movieResult.getOrNull()?.results?.let {
                            _showsLiveData.postValue(it)
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
    class Factory(private val repository: TVShowsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TVShowsVM(repository) as T
        }
    }
}