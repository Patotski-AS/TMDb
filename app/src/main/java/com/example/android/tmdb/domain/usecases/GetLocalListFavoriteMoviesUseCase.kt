package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.domain.repository.FavoriteRepository

class GetLocalListFavoriteMoviesUseCase(private val repository: FavoriteRepository) {
    operator fun invoke() = repository.getAllMovies()
}
