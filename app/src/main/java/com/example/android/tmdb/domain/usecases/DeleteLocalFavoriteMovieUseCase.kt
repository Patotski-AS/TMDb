package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.domain.repository.FavoriteRepository

class DeleteLocalFavoriteMovieUseCase(private val repository: FavoriteRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteFavorite(id)
}
