package com.noha.moviesadvanced.data.source.repository

import com.noha.moviesadvanced.data.source.mapper.toDomainModel
import com.noha.moviesadvanced.data.source.network.MoviesAPIs
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.data.source.network.movieAPIs
import com.noha.moviesadvanced.data.source.network.safeApiCall
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


val moviesRepository: MoviesRepository by lazy {
    MoviesRepositoryImpl(movieAPIs)
}

class MoviesRepositoryImpl(
    private val moviesAPIs: MoviesAPIs,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {


    override suspend fun getMovieList(pageNum: Int): ResponseWrapper<List<Movie>> {
        return withContext(dispatcher) {
            val response = safeApiCall {
                moviesAPIs.getPopularMoviesAsync(page = pageNum)
            }
            when (response) {
                is ResponseWrapper.Success -> {
                    return@withContext ResponseWrapper.Success(response.value.results.toDomainModel())
                }
                is ResponseWrapper.GenericError -> return@withContext ResponseWrapper.GenericError(
                    response.code,
                    response.error
                )
                else -> return@withContext ResponseWrapper.NetworkError
            }
        }
    }


    /**
     * Get movie director and actors
     */
    override suspend fun getMovieCast(movie: Movie): ResponseWrapper<Movie> {
        return withContext(dispatcher) {
            val response = safeApiCall {
                moviesAPIs.getMovieCastAndCrewAsync(movie.id)
            }

            when (response) {
                is ResponseWrapper.Success -> {
                    val castResponse = response.value

                    //Get director from the crew list
                    movie.director =
                        castResponse.crew.firstOrNull { it.job == "Director" }?.name ?: ""

                    movie.actors = castResponse.cast.toDomainModel()

                    return@withContext ResponseWrapper.Success(movie)
                }
                is ResponseWrapper.GenericError -> return@withContext ResponseWrapper.GenericError(
                    response.code,
                    response.error
                )
                else -> return@withContext ResponseWrapper.NetworkError
            }
        }
    }
}
