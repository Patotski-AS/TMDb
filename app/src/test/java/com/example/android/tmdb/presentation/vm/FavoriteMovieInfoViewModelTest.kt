package com.example.android.tmdb.presentation.vm


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.tmdb.helpers.TestCoroutinesRule
import com.example.android.tmdb.helpers.TestUtil
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.usecases.DeleteLocalFavoriteMovieUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class FavoriteMovieInfoViewModelTest {

    @get:Rule
    var coroutinesRule = TestCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var testViewModel: FavoriteMovieInfoViewModel
    private lateinit var mockMovie: MovieInfo

    @MockK
    private lateinit var testUseCase: DeleteLocalFavoriteMovieUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val testId = 587822L
        testViewModel = FavoriteMovieInfoViewModel(testUseCase)
        mockMovie = TestUtil.createFakeMovieInfo(testId)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testFavoriteMovieInfoViewModel_callFunSetMovieInfo_movieInfoRefresh() = runBlockingTest {
        testViewModel.setMovieInfo(mockMovie)
        val movieResponse = testViewModel.movieInfo.value
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse, `is`(mockMovie))
    }

    @Test
    fun testFavoriteMovieInfoViewModel_callDeleteFavoriteMovie_callDeleteLocalFavoriteMovieUseCase() =
        runBlockingTest {
            coEvery { testUseCase.invoke(any()) } returns Unit
            testViewModel.setMovieInfo(mockMovie)
            testViewModel.deleteFavoriteMovie()
            coVerify { testUseCase.invoke(any()) }
        }
}