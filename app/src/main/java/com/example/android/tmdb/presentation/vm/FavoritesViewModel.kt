package com.example.android.tmdb.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.usecases.GetLocalListFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getLocalListFavoriteMoviesUseCase: GetLocalListFavoriteMoviesUseCase
) : ViewModel() {

    private val _favoriteMovies = getLocalListFavoriteMoviesUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val favoriteMovies: StateFlow<List<MovieInfo>> = _favoriteMovies

}
