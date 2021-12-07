package com.example.android.tmdb.helpers.fake

import androidx.paging.PagingData
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository(
    private val listMovies: ArrayList<MovieInfo>
) : MovieRepository {

    override fun getMovies(): Flow<PagingData<MovieInfo>> {
        return flow {
            val list = PagingData.from(listMovies)
            emit(list)
        }
    }

    override fun getSearchResults(query: String): Flow<PagingData<MovieInfo>> {
        return if (query.isBlank() || query == "")
            flow {
                val list = PagingData.empty<MovieInfo>()
                emit(list)
            } else
            flow {
                val list = PagingData.from(listMovies)
                emit(list)
            }
    }

    override suspend fun getMovieById(id: Long): MovieInfo? {
        return listMovies.find { it.id == id }
    }
}