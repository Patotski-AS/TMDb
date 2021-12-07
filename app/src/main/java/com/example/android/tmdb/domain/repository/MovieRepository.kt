package com.example.android.tmdb.domain.repository

import androidx.paging.PagingData
import com.example.android.tmdb.domain.models.MovieInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<MovieInfo>>
    fun getSearchResults(query:String): Flow<PagingData<MovieInfo>>
    suspend fun getMovieById(id: Long): MovieInfo?
}
