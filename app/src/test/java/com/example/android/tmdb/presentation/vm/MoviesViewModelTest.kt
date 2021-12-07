package com.example.android.tmdb.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.usecases.GetLocalListFavoriteMoviesUseCase
import com.example.android.tmdb.domain.usecases.GetLocalListMovieUseCase
import com.example.android.tmdb.domain.usecases.GetRemoveListSearchResultsUseCase
import com.example.android.tmdb.helpers.TestCoroutinesRule
import com.example.android.tmdb.helpers.TestUtil
import com.example.android.tmdb.helpers.fake.FakeFavoriteRepository
import com.example.android.tmdb.helpers.fake.FakeMovieRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MoviesViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var testViewModel: MoviesViewModel
    private lateinit var mockListMovies: ArrayList<MovieInfo>

    @MockK
    private lateinit var getRemoveListSearchResultsUseCase: GetRemoveListSearchResultsUseCase

    @MockK
    private lateinit var getLocalListMovieUseCase: GetLocalListMovieUseCase

    @MockK
    private lateinit var getLocalListFavoriteMoviesUseCase: GetLocalListFavoriteMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val testMovieFirst = TestUtil.createFakeMovieInfo(5878L)
        mockListMovies = arrayListOf(testMovieFirst)
        val testRepository = FakeMovieRepository(mockListMovies)
        val testFavoriteRepository = FakeFavoriteRepository(mockListMovies)
        getRemoveListSearchResultsUseCase = GetRemoveListSearchResultsUseCase(testRepository)
        getLocalListMovieUseCase = GetLocalListMovieUseCase(testRepository)
        getLocalListFavoriteMoviesUseCase =
            GetLocalListFavoriteMoviesUseCase(testFavoriteRepository)
        testViewModel = MoviesViewModel(
            getRemoveListSearchResultsUseCase,
            getLocalListMovieUseCase,
            getLocalListFavoriteMoviesUseCase
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testMoviesViewModel_callSetQuery_queryRefresh() = runBlockingTest {
        val query = "test"
        testViewModel.setQuery(query)
        val movieResponse = testViewModel.query.value
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse, `is`(query))
    }

    @Test
    fun testMoviesViewModel_gettingLocalListFavoriteMovies() = runBlockingTest {
        val movieResponse = getLocalListFavoriteMoviesUseCase.invoke()
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse.first()[0], `is`(mockListMovies[0]))
    }

    @Test
    fun testMoviesViewModel_gettingUpdatedLocalListFavoriteMovies() = runBlockingTest {
        val movieResponse = getLocalListFavoriteMoviesUseCase.invoke()
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse.first()[0], `is`(mockListMovies[0]))
        val testMovieSecond = TestUtil.createFakeMovieInfo(1111L)
        mockListMovies.add(testMovieSecond)
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse.first()[0], `is`(mockListMovies[0]))
        assertThat(movieResponse.first()[1], `is`(mockListMovies[1]))
    }
}