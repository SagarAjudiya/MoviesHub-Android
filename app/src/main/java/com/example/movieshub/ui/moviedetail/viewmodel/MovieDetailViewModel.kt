package com.example.movieshub.ui.moviedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieshub.data.model.response.Cast
import com.example.movieshub.ui.home.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {

    private val castLiveData = MutableLiveData<List<Cast>>()
    val castData: LiveData<List<Cast>>
        get() = castLiveData

    fun getCastDetail(movieId: Int) {
        viewModelScope.launch {
            val repository = MovieRepository()
            val castResponse = repository.getCast(movieId)
            castResponse?.cast?.let { cast ->
                castLiveData.postValue(cast)
            }
        }
    }

}