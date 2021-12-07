package com.example.android.tmdb.data.paging

import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.tmdb.data.api.TMDbApiService
import com.example.android.tmdb.data.db.TMDbDatabase
import com.example.android.tmdb.data.db.entity.MovieEntity
import com.example.android.tmdb.data.mappers.MapMoviePogoToMovieEntity
import com.example.android.tmdb.data.utill.getValueLanguage
import com.example.android.tmdb.data.utill.getValueSort
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val database: TMDbDatabase,
    private val apiService: TMDbApiService,
    private val mapMoviePogoToMovieEntity: MapMoviePogoToMovieEntity,
    private val preferences: SharedPreferences
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val movie = getClosestMovie(state)
                movie?.nextKey?.minus(1)
                    ?: STARTING_PAGE
            }
            LoadType.PREPEND -> {
                val movie = getMovieForFirstItem(state)
                val prevKey = movie?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = movie != null)
                prevKey
            }
            LoadType.APPEND -> {
                val movie = getMovieForLastItem(state)
                val nextKey = movie?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = movie != null)
                nextKey
            }
        }
        try {
            val language = preferences.getValueLanguage()
            val sort = preferences.getValueSort()
            val apiResponse =
                apiService.getMovies(page = page.toString(), sorted = sort, language = language)
            val movies = apiResponse.results
            val endOfPaginationReached = movies.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.moviesDAO().clearMovies()
                }
                val prevKey = if (page == STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val movieEntities = movies.map { movie ->
                    mapMoviePogoToMovieEntity.map(movie).apply {
                        this.prevKey = prevKey
                        this.nextKey = nextKey
                    }
                }
                database.moviesDAO().insert(movieEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getMovieForLastItem(state: PagingState<Int, MovieEntity>): MovieEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie ->
                database.moviesDAO().getMovieById(movie.id)
            }
    }

    private suspend fun getMovieForFirstItem(state: PagingState<Int, MovieEntity>): MovieEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> database.moviesDAO().getMovieById(movie.id) }
    }

    private suspend fun getClosestMovie(state: PagingState<Int, MovieEntity>): MovieEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                database.moviesDAO().getMovieById(movieId)
            }
        }
    }
}
