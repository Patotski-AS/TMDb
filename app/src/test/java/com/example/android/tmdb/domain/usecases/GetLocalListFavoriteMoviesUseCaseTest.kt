package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.helpers.TestUtil
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.repository.FavoriteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLocalListFavoriteMoviesUseCaseTest : TestCase() {

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
    fun testGetLocalListFavoriteMoviesUseCase_ifMoviesIsInDB_returnFlowListMovies() =
        runBlockingTest {
            //Given
            val testMovieFirst = TestUtil.createFakeMovieInfo(5878L)
            val testMovieSecond = TestUtil.createFakeMovieInfo(587822L)
            val useCase = GetLocalListFavoriteMoviesUseCase(repository)

            val mockListMovies = flow {
                val list = listOf(testMovieFirst, testMovieSecond)
                emit(list)
            }

            //When
            coEvery { repository.getAllMovies() }.returns(mockListMovies)

            //Invoke
            val movieResponse = useCase.invoke()

            //Then
            assertThat(movieResponse, notNullValue())
            assertThat(movieResponse.first()[0], `is`(testMovieFirst))
            assertThat(movieResponse.first()[1], `is`(testMovieSecond))
        }

    @Test
    fun testGetLocalListFavoriteMoviesUseCase_ifDBEmpty_returnFlowEmptyList() = runBlockingTest {
        //Given
        val useCase = GetLocalListFavoriteMoviesUseCase(repository)

        val mockListMovies = flow {
            val list = emptyList<MovieInfo>()
            emit(list)
        }

        //When
        coEvery { repository.getAllMovies() }.returns(mockListMovies)

        //Invoke
        val movieResponse = useCase.invoke()

        //Then
        assertThat(movieResponse, notNullValue())
        assertThat(movieResponse.first(), `is`(emptyList()))
    }
}