package com.noha.moviesadvanced.presentaion.ui;

import androidx.lifecycle.*
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.domain.repository.MoviesRepository
import kotlinx.coroutines.launch


class MainViewModel(val repository: MoviesRepository) : ViewModel() {

    private var pageNumber: Int = 1

    val moviesResponse: LiveData<ResponseWrapper<List<Movie>>>
        get() = _moviesResponse
    private val _moviesResponse: MutableLiveData<ResponseWrapper<List<Movie>>> = MutableLiveData()

    val selectedMovieDetails: LiveData<ResponseWrapper<Movie>>
        get() = _selectedMovieDetails
    private val _selectedMovieDetails: MutableLiveData<ResponseWrapper<Movie>> = MutableLiveData()

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            _moviesResponse.postValue(repository.getMovieList(pageNumber))
        }
    }

    fun getMovieDetails(movie: Movie) {
        viewModelScope.launch {
            _selectedMovieDetails.postValue(repository.getMovieCast(movie))
        }
    }


    class Factory constructor(private val repository: MoviesRepository) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel(this.repository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}
