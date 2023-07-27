package com.example.movieshub.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.ui.home.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val popularMoviesLiveData = MutableLiveData<MovieResponse?>()
    val popularMovies: LiveData<MovieResponse?>
        get() = popularMoviesLiveData

    private val inTheatresMoviesList = MutableLiveData<MovieResponse?>()
    val inTheatresMovie: LiveData<MovieResponse?>
        get() = inTheatresMoviesList

    private val categoryLiveData = MutableLiveData<List<String>>()
    val categoryData: MutableLiveData<List<String>>
        get() = categoryLiveData

    fun getPopularMovieList() {
        viewModelScope.launch {
            val movieResponse = repository.getMovies("popular")
            popularMoviesLiveData.postValue(movieResponse)
        }
    }

    fun getInTheatresMovies() {
        viewModelScope.launch {
            val movieResponse = repository.getMovies("now_playing")
            inTheatresMoviesList.postValue(movieResponse)
        }
    }

    fun getCategoriesDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryLiveData.postValue(repository.getCategories())
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: MovieRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }

}