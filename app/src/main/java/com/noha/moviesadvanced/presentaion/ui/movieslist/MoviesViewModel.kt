package com.noha.moviesadvanced.presentaion.ui.movieslist

import androidx.lifecycle.*
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.domain.repository.MoviesRepository
import com.noha.moviesadvanced.presentaion.ui.BaseViewModel
import kotlinx.coroutines.launch


class MoviesViewModel(private val repository: MoviesRepository) : BaseViewModel() {

    private var pageNumber: Int = 1

    val moviesList: LiveData<List<Movie>>
        get() = _moviesList
    private val _moviesList: MutableLiveData<List<Movie>> = MutableLiveData()

    val currentMovie: LiveData<Movie>
        get() = _currentMovie
    private val _currentMovie: MutableLiveData<Movie> = MutableLiveData()

    init {
        getMovieList()
    }

    private fun getMovieList() {
        _isLoading.value = true

        viewModelScope.launch {
            when (val response = repository.getMovieList(pageNumber)) {
                is ResponseWrapper.Success -> {
                    val movies = response.value as List<Movie>
                    _moviesList.postValue(movies)
                    _currentMovie.value = movies[0]
                }
                else -> onResponseError(response)
            }
            _isLoading.value = false
        }
    }

    fun onCurrentMovieChanged(movieIndex: Int) {
        _currentMovie.value = moviesList.value?.get(movieIndex)
    }

    class Factory constructor(private val repository: MoviesRepository) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
                MoviesViewModel(this.repository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}
