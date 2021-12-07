package com.example.android.tmdb.domain.usecases

import androidx.paging.PagingData
import com.example.android.tmdb.helpers.TestUtil
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetLocalListMovieUseCaseTest : TestCase() {

    @MockK
    private lateinit var repository: MovieRepository

    @Before
    public override fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun testGetLocalListMovieUseCase_ifMoviesIsInDB_returnFlowPagingDataMovies() = runBlocking {
        //Given
        val testMovieFirst = TestUtil.createFakeMovieInfo(5878L)
        val testMovieSecond = TestUtil.createFakeMovieInfo(587822L)
        val useCase = GetLocalListMovieUseCase(repository)

        val list = PagingData.from(listOf(testMovieFirst, testMovieSecond))
        val mockListMovies = flow {
            emit(list)
        }

        //When
        coEvery { repository.getMovies() }.returns(mockListMovies)

        //Invoke
        val movieResponse = useCase.invoke()

        //Then
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse.first(), `is`(list))
    }

    @Test
    fun testGetLocalListMovieUseCase_ifDBEmpty_returnFlowPagingDataEmpty() = runBlocking {
        //Given
        val useCase = GetLocalListMovieUseCase(repository)

        val mockListMovies = flow {
            val list = PagingData.empty<MovieInfo>()
            emit(list)
        }

        //When
        coEvery { repository.getMovies() }.returns(mockListMovies)

        //Invoke
        val movieResponse = useCase.invoke()

        //Then
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse.first(), `is`(PagingData.empty()))
    }

}