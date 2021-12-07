package com.example.android.tmdb.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.usecases.GetLocalListFavoriteMoviesUseCase
import com.example.android.tmdb.domain.usecases.GetLocalListMovieUseCase
import com.example.android.tmdb.domain.usecases.GetRemoveListSearchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val DEFAULT_QUERY = ""

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getRemoveListSearchResultsUseCase: GetRemoveListSearchResultsUseCase,
    private val getLocalListMovieUseCase: GetLocalListMovieUseCase,
    private val getLocalListFavoriteMoviesUseCase: GetLocalListFavoriteMoviesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow(DEFAULT_QUERY)
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _searchResults = _query
        .flatMapLatest { getMoviesInfo(it) }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    val searchResults: StateFlow<PagingData<MovieInfo>>
        get() = _searchResults

    private val _favoriteMovies = getLocalListFavoriteMoviesUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val favoriteMovies: StateFlow<List<MovieInfo>> = _favoriteMovies

    private fun getMoviesInfo(query: String) = when (query) {
        DEFAULT_QUERY -> getLocalListMovieUseCase.invoke()
        else -> getRemoveListSearchResultsUseCase.invoke(query)
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }
}
