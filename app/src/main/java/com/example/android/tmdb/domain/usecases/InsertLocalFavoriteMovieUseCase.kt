package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.repository.FavoriteRepository

class InsertLocalFavoriteMovieUseCase(private val repository: FavoriteRepository) {
    suspend operator fun invoke(movieInfo: MovieInfo) = repository.insert(movieInfo)
}
