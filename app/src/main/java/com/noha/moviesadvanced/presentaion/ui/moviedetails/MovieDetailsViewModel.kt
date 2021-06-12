package com.noha.moviesadvanced.presentaion.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.domain.repository.MoviesRepository
import com.noha.moviesadvanced.presentaion.ui.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    movie: Movie,
    val previousImage: String,
    val nextImage: String,
    private val repository: MoviesRepository
) : BaseViewModel() {

    val movieDetails: MutableLiveData<Movie> = MutableLiveData(movie)

    init {
        getMovieDetails(movie)
    }

    private fun getMovieDetails(movie: Movie) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val response = repository.getMovieCast(movie)) {
                is ResponseWrapper.Success -> movieDetails.postValue(response.value as Movie?)
                else -> onResponseError(response)
            }
            _isLoading.value = false
        }
    }

    class Factory constructor(
        private val movie: Movie,
        private val previousImage: String,
        private val nextImage: String,
        private val repository: MoviesRepository
    ) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                MovieDetailsViewModel(
                    movie,
                    previousImage,
                    nextImage,
                    this.repository
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}