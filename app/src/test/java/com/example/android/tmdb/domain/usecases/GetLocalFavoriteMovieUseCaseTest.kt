package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.helpers.TestUtil
import com.example.android.tmdb.domain.repository.FavoriteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetLocalFavoriteMovieUseCaseTest : TestCase() {

    @MockK
    private lateinit var repository: FavoriteRepository

    @Before
    public override fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun testGetLocalFavoriteMovie_ifMovieIsInDB_returnMovieInfo() = runBlocking {
        //Given
        val testId = 587822L
        val useCase = GetLocalFavoriteMovieUseCase(repository)
        val mockMovie = TestUtil.createFakeMovieInfo(testId)

        //When
        coEvery { repository.getMovieById(any()) }.returns(mockMovie)

        //Invoke
        val movieResponse = useCase.invoke(testId)

        //Then
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse, `is`(mockMovie))
        assertThat(movieResponse?.bigPosterPath, `is`(mockMovie.bigPosterPath))
        assertThat(movieResponse?.popularity, `is`(mockMovie.popularity))
        assertThat(movieResponse?.id, `is`(mockMovie.id))
    }

    @Test
    fun testGetLocalFavoriteMovie_ifMovieNotInDB_returnNull() = runBlocking {
        //Given
        val testId = 0L
        val useCase = GetLocalFavoriteMovieUseCase(repository)

        //When
        coEvery { repository.getMovieById(any()) }.returns(null)

        //Invoke
        val movieResponse = useCase.invoke(testId)

        //Then
        assertThat(movieResponse, nullValue())
    }
}
