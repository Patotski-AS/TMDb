package com.example.android.tmdb.domain.repository

import com.example.android.tmdb.domain.models.MovieInfo
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insert(movieInfo: MovieInfo)
    suspend fun getMovieById(id: Long): MovieInfo?
    suspend fun deleteFavorite(id: Long)
    fun getAllMovies(): Flow<List<MovieInfo>>
}