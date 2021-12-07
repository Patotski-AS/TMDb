package com.example.android.tmdb.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.tmdb.helpers.TestCoroutinesRule
import com.example.android.tmdb.helpers.TestUtil
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.usecases.DeleteLocalFavoriteMovieUseCase
import com.example.android.tmdb.domain.usecases.InsertLocalFavoriteMovieUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MovieInfoViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var testViewModel: MovieInfoViewModel
    private lateinit var mockMovie: MovieInfo

    @MockK
    private lateinit var insertLocalFavoriteMovieUseCase: InsertLocalFavoriteMovieUseCase

    @MockK
    private lateinit var deleteLocalFavoriteMovieUseCase: DeleteLocalFavoriteMovieUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val testId = 587822L
        mockMovie = TestUtil.createFakeMovieInfo(testId)
        testViewModel = MovieInfoViewModel(
            insertLocalFavoriteMovieUseCase,
            deleteLocalFavoriteMovieUseCase
        )
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
    fun testMovieInfoViewModel_callAddFavoriteMovie_callInsertLocalFavoriteMovieUseCase() =
        runBlockingTest {
            coEvery { insertLocalFavoriteMovieUseCase.invoke(any()) } returns Unit
            testViewModel.setMovieInfo(mockMovie)
            testViewModel.addFavoriteMovie()
            coVerify { insertLocalFavoriteMovieUseCase.invoke(any()) }
        }

    @Test
    fun testMovieInfoViewModel_callDeleteFavoriteMovie_callDeleteLocalFavoriteMovieUseCase() =
        runBlockingTest {
            coEvery { deleteLocalFavoriteMovieUseCase.invoke(any()) } returns Unit
            testViewModel.setMovieInfo(mockMovie)
            testViewModel.deleteFavoriteMovie()
            coVerify { deleteLocalFavoriteMovieUseCase.invoke(any()) }
        }
}