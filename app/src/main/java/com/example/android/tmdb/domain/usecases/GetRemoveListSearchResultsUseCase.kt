package com.example.android.tmdb.domain.usecases

import com.example.android.tmdb.domain.repository.MovieRepository

class GetRemoveListSearchResultsUseCase(private val repository: MovieRepository) {
    operator fun invoke(query: String) = repository.getSearchResults(query = query)
}
