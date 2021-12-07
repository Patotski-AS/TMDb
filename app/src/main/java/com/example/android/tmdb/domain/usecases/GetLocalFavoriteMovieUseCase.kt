package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.domain.repository.FavoriteRepository

class GetLocalFavoriteMovieUseCase(private val repository: FavoriteRepository) {
    suspend operator fun invoke(id: Long) = repository.getMovieById(id)
}
