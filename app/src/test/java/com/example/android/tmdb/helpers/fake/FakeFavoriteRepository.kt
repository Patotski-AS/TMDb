package com.example.android.tmdb.helpers.fake

import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class FakeFavoriteRepository(
    private val listMovie: ArrayList<MovieInfo>
) : FavoriteRepository {

    override suspend fun insert(movieInfo: MovieInfo) {
        listMovie.add(movieInfo)
    }

    override suspend fun getMovieById(id: Long): MovieInfo? {
        return listMovie.find { it.id == id }
    }

    override suspend fun deleteFavorite(id: Long) {
        listMovie.remove(listMovie.find { it.id == id })
    }

    override fun getAllMovies(): Flow<List<MovieInfo>> {
        return flow {
            val list = listMovie
            emit(list)
        }
    }
}