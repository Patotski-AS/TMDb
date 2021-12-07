package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flowOf

class GetLocalMovieInfoUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(id: Long) = flowOf(repository.getMovieById(id))
}
