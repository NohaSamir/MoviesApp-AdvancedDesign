package com.noha.moviesadvanced.presentaion.ui.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.noha.moviesadvanced.data.source.network.model.ErrorResponse
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.domain.model.Actor
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.domain.repository.MoviesRepository
import com.noha.moviesadvanced.presentaion.ui.moviedetails.MovieDetailsViewModel
import com.noha.moviesadvanced.utils.CoroutineTestRule
import com.noha.moviesadvanced.utils.getOrAwaitValue
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    private val movie = Movie(
        1, "movie", " ", "", "",
        5f, "Director", listOf(Actor("Actor", ""))
    )

    private lateinit var repository: MoviesRepository
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun testWhenViewModelInit_VerifyFetchMovieDetailsCalled() {
        // Given
        coEvery { repository.getMovieCast(movie) } returns mockk()

        // When
        viewModel = MovieDetailsViewModel(movie, "", "", repository)

        // Then
        coVerify { repository.getMovieCast(movie) }
    }

    @Test
    fun testWhenMovieDetailsLoading_VerifyLoading() {

        mainCoroutineRule.pauseDispatcher()

        // Load the task in the view model.
        coEvery { repository.getMovieCast(movie) } returns mockk(relaxed = true)
        viewModel = MovieDetailsViewModel(movie, "", "", repository)

        // Then assert that the progress indicator is shown.
        assertEquals(true, viewModel.isLoading.getOrAwaitValue())

        // Execute pending coroutines actions.
        mainCoroutineRule.resumeDispatcher()

        // Then assert that the progress indicator is hidden.
        assertEquals(false, viewModel.isLoading.getOrAwaitValue())

    }

    @Test
    fun testWhenMovieDetailReturnedSuccessfully_VerifyMovieDetailChanged() {
        val successResponse: ResponseWrapper.Success<Movie> = ResponseWrapper.Success(movie)
        coEvery { repository.getMovieCast(movie) } returns successResponse

        viewModel = MovieDetailsViewModel(movie, "", "", repository)

        assertEquals(successResponse.value, viewModel.movieDetails.getOrAwaitValue())
    }

    @Test
    fun testWhenMovieDetailsReturnedError_VerifyShowErrorMessage() {
        val errorResponse = ResponseWrapper.GenericError(0, error = ErrorResponse(0, "Error"))
        coEvery { repository.getMovieCast(movie) } returns errorResponse

        viewModel = MovieDetailsViewModel(movie, "", "", repository)

        assertEquals(errorResponse.error?.message, viewModel.showMessage.getOrAwaitValue())
    }


    @Test
    fun testMovieDetailsViewModelFactory() {
        coEvery { repository.getMovieCast(movie) } returns mockk(relaxed = true)

        val viewModel = MovieDetailsViewModel.Factory(movie, "", "", repository)
            .create(MovieDetailsViewModel::class.java)

        assert(viewModel is MovieDetailsViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMovieDetailsViewModelFactoryOfUnknownType_VerifyExceptionThrown() {
        MovieDetailsViewModel.Factory(movie, "", "", repository)
            .create(MoviesViewModel::class.java)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

}