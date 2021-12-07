package com.example.android.tmdb.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.usecases.DeleteLocalFavoriteMovieUseCase
import com.example.android.tmdb.domain.usecases.InsertLocalFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val insertLocalFavoriteMovieUseCase: InsertLocalFavoriteMovieUseCase,
    private val deleteLocalFavoriteMovieUseCase: DeleteLocalFavoriteMovieUseCase
) : ViewModel() {

    private var _movieInfo = MutableStateFlow(MovieInfo())
    val movieInfo: StateFlow<MovieInfo> = _movieInfo.asStateFlow()

    fun setMovieInfo(movieInfo: MovieInfo) {
        _movieInfo.tryEmit(movieInfo)
    }

    fun addFavoriteMovie() {
        viewModelScope.launch {
            insertLocalFavoriteMovieUseCase.invoke(movieInfo.value)
        }
    }

    fun deleteFavoriteMovie() {
        viewModelScope.launch {
            deleteLocalFavoriteMovieUseCase.invoke(movieInfo.value.id)
        }
    }
}
