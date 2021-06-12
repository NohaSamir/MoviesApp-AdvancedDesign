package com.noha.moviesadvanced.presentaion.ui.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.noha.moviesadvanced.data.source.network.model.ErrorResponse
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
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
class MoviesViewModelTest {

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    private lateinit var repository: MoviesRepository
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun testWhenViewModelInit_VerifyFetchMoviesCalled() {
        // Given
        coEvery { repository.getMovieList(1) } returns mockk()

        // When
        viewModel = MoviesViewModel(repository)

        // Then
        coVerify { repository.getMovieList(1) }
    }

    @Test
    fun testWhenMoviesListLoading_VerifyLoading() {

        mainCoroutineRule.pauseDispatcher()

        // Load the task in the view model.
        coEvery { repository.getMovieList(1) } returns mockk(relaxed = true)
        viewModel = MoviesViewModel(repository)

        // Then assert that the progress indicator is shown.
        assertEquals(true, viewModel.isLoading.getOrAwaitValue())

        // Execute pending coroutines actions.
        mainCoroutineRule.resumeDispatcher()

        // Then assert that the progress indicator is hidden.
        assertEquals(false, viewModel.isLoading.getOrAwaitValue())

    }

    @Test
    fun testWhenMoviesListReturnedSuccessfully_VerifyMoviesListChanged() {
        val successResponse: ResponseWrapper.Success<List<Movie>> =
            ResponseWrapper.Success(mockk(relaxed = true))
        coEvery { repository.getMovieList(1) } returns successResponse

        viewModel = MoviesViewModel(repository)

        assertEquals(successResponse.value, viewModel.moviesList.getOrAwaitValue())
    }

    @Test
    fun testWhenMoviesListReturnedError_VerifyShowErrorMessage() {
        val errorResponse = ResponseWrapper.GenericError(0, error = ErrorResponse(0, "Error"))
        coEvery { repository.getMovieList(1) } returns errorResponse

        viewModel = MoviesViewModel(repository)

        assertEquals(errorResponse.error?.message, viewModel.showMessage.getOrAwaitValue())
    }

    @Test
    fun testOnCurrentMovieChange_VerifyCurrentMovieChanged() {
        val movies = listOf(
            Movie(1, "", "", "", ""),
            Movie(2, "", "", "", "")
        )
        coEvery { repository.getMovieList(1) } returns ResponseWrapper.Success(movies)
        viewModel = MoviesViewModel(repository)

        viewModel.onCurrentMovieChanged(1)

        assertEquals(movies[1], viewModel.currentMovie.getOrAwaitValue())
    }

    @Test
    fun testMoviesViewModelFactory() {
        coEvery { repository.getMovieList(1) } returns mockk(relaxed = true)

        val viewModel = MoviesViewModel.Factory(repository).create(MoviesViewModel::class.java)

        assert(viewModel is MoviesViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMoviesViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        MoviesViewModel.Factory(repository).create(MovieDetailsViewModel::class.java)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

}