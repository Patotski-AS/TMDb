package com.example.android.tmdb.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.usecases.GetLocalListFavoriteMoviesUseCase
import com.example.android.tmdb.helpers.TestCoroutinesRule
import com.example.android.tmdb.helpers.TestUtil
import com.example.android.tmdb.helpers.fake.FakeFavoriteRepository
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
class FavoritesViewModelTest {

    @get:Rule
    var coroutinesRule = TestCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var testViewModel: FavoritesViewModel
    private lateinit var mockListMovies: ArrayList<MovieInfo>

    @MockK
    private lateinit var getLocalListFavoriteMoviesUseCase: GetLocalListFavoriteMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val testMovieFirst = TestUtil.createFakeMovieInfo(5878L)
        mockListMovies = arrayListOf(testMovieFirst)
        val testFavoriteRepository = FakeFavoriteRepository(mockListMovies)
        getLocalListFavoriteMoviesUseCase =
            GetLocalListFavoriteMoviesUseCase(testFavoriteRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testFavoritesViewModel_gettingDateFromDB() = runBlockingTest {
        testViewModel = FavoritesViewModel(getLocalListFavoriteMoviesUseCase)
        val movieResponse = getLocalListFavoriteMoviesUseCase.invoke()
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse.first()[0], `is`(mockListMovies[0]))
    }

    @Test
    fun testFavoritesViewModel_gettingUpdatedDateFromDB() = runBlockingTest {
        testViewModel = FavoritesViewModel(getLocalListFavoriteMoviesUseCase)
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