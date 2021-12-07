package com.example.android.tmdb.data.repositories

import android.content.SharedPreferences
import androidx.paging.*
import com.example.android.tmdb.data.api.TMDbApiService
import com.example.android.tmdb.data.db.TMDbDatabase
import com.example.android.tmdb.data.mappers.MapMovieEntityToMovieInfo
import com.example.android.tmdb.data.mappers.MapMoviePogoToMovieEntity
import com.example.android.tmdb.data.mappers.MapSearchResultsPogoToMovieInfo
import com.example.android.tmdb.data.paging.MoviesRemoteMediator
import com.example.android.tmdb.data.paging.SearchPagingSource
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STARTING_PAGE_SIZE = 40

class MovieRepositoryImpl(
    private val database: TMDbDatabase,
    private val apiService: TMDbApiService,
    private val mapMoviePogoToMovieEntity: MapMoviePogoToMovieEntity,
    private val mapSearchResultsPogoToMovieInfo: MapSearchResultsPogoToMovieInfo,
    private val mapMovieEntityToMovieInfo: MapMovieEntityToMovieInfo,
    private val preferences: SharedPreferences
) : MovieRepository {
    override fun getMovies(): Flow<PagingData<MovieInfo>> {
        val pagingSourceFactory = { database.moviesDAO().getAllMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = STARTING_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = MoviesRemoteMediator(
                database = database,
                apiService = apiService,
                mapMoviePogoToMovieEntity = mapMoviePogoToMovieEntity,
                preferences = preferences
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                mapMovieEntityToMovieInfo.map(it).apply {
                    isFavorite = isFavorite(id)
                }
            }
        }
    }

    override fun getSearchResults(query: String): Flow<PagingData<MovieInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = STARTING_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    service = apiService,
                    query = query,
                    preferences = preferences
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                mapSearchResultsPogoToMovieInfo.map(it).apply {
                    this.isFavorite = isFavorite(id)
                }
            }
        }
    }

    override suspend fun getMovieById(id: Long): MovieInfo? {
        return database.moviesDAO().getMovieById(key = id)
            ?.let { mapMovieEntityToMovieInfo.map(it) }
    }

    private suspend fun isFavorite(id: Long): Boolean {
        return database.favoritesDAO().getMovieById(key = id) != null
    }
}
