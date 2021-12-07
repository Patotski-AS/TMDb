package com.example.android.tmdb.data.paging

import android.content.SharedPreferences
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.tmdb.data.api.TMDbApiService
import com.example.android.tmdb.data.api.pogo.SearchResultsPogo
import com.example.android.tmdb.data.utill.getValueLanguage
import retrofit2.HttpException

private const val STARTING_PAGE = 1

class SearchPagingSource(
    private val service: TMDbApiService,
    private val query: String,
    private val preferences: SharedPreferences
) : PagingSource<Int, SearchResultsPogo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultsPogo> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        try {
            val language = preferences.getValueLanguage()
            val position = params.key ?: STARTING_PAGE
            val response = service.getResultSearch(
                query = query,
                page = position.toString(),
                language = language
            )

            return if (response.isSuccessful) {
                val searchResults = response.body()!!.searchResultPogo
                val nextKey = if (searchResults.isEmpty()) null else position + 1
                val prevKey = if (position == STARTING_PAGE) null else position - 1
                LoadResult.Page(data = searchResults, prevKey = prevKey, nextKey = nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchResultsPogo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
