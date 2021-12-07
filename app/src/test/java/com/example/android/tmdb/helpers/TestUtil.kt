package com.example.android.tmdb.helpers

import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.setBigPosterPath
import com.example.android.tmdb.domain.utill.setSmallPosterPath

class TestUtil {
    companion object {
        fun createFakeMovieInfo(id: Long) = MovieInfo(
                id = id,
                adult = true,
                backdropPath = "backdropPath$id",
                genreIds = listOf(1, 2, 3),
                originalLanguage = "originalLanguage+ id",
                originalTitle = "originalTitle$id",
                overview = "overview$id",
                popularity = 2.2,
                smallPosterPath = "posterPath$id".setSmallPosterPath(),
                bigPosterPath = "posterPath$id".setBigPosterPath(),
                releaseDate = "releaseDate$id",
                title = "title$id",
                video = false,
                voteAverage = 1.1,
                voteCount = 5,
                isFavorite = false
            )

    }
}