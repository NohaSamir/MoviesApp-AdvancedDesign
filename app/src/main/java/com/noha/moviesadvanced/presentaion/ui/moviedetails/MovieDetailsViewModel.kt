package com.noha.moviesadvanced.presentaion.ui.moviedetails

import androidx.lifecycle.*
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.domain.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    movie: Movie,
    private val repository: MoviesRepository
) : ViewModel() {

    val movieDetails: MutableLiveData<Movie> = MutableLiveData(movie)

    val error: LiveData<ResponseWrapper<Any>>
        get() = _error
    private val _error: MutableLiveData<ResponseWrapper<Any>> = MutableLiveData()

    init {
        getMovieDetails(movie)
    }

    private fun getMovieDetails(movie: Movie) {
        viewModelScope.launch {
            when (val response = repository.getMovieCast(movie)) {
                is ResponseWrapper.Success -> movieDetails.postValue(response.value)
                else -> _error.postValue(response)
            }
        }
    }

    class Factory constructor(private val movie: Movie, private val repository: MoviesRepository) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                MovieDetailsViewModel(movie, this.repository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}