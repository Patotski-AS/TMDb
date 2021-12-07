package com.example.android.tmdb.data.api

import com.example.android.tmdb.data.api.pogo.MovieResponse
import com.example.android.tmdb.data.api.pogo.SearchResponse
import retrofit2.Response
import retrofit2.http.GET

private const val PARAMS_LANGUAGE = "language"
private const val PARAMS_SORT_BY = "sort_by"
private const val PARAMS_PAGE = "page"
private const val PARAMS_QUERY = "query"
private const val PARAMS_VOTE_COUNT = "vote_count.gte"

private const val VALUE_VOTE_COUNT = 1000

interface TMDbApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @retrofit2.http.Query(PARAMS_LANGUAGE) language: String,
        @retrofit2.http.Query(PARAMS_SORT_BY) sorted: String,
        @retrofit2.http.Query(PARAMS_VOTE_COUNT) vote_count: Int = VALUE_VOTE_COUNT,
        @retrofit2.http.Query(PARAMS_PAGE) page: String
    ): MovieResponse

    @GET("search/movie")
    suspend fun getResultSearch(
        @retrofit2.http.Query(PARAMS_LANGUAGE) language: String,
        @retrofit2.http.Query(PARAMS_QUERY) query: String,
        @retrofit2.http.Query(PARAMS_PAGE) page: String
    ): Response<SearchResponse>
}
