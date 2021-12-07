package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.domain.repository.MovieRepository

class GetLocalListMovieUseCase(private val repository: MovieRepository) {
    operator fun invoke() = repository.getMovies()
}
