package com.example.android.tmdb.data.mappers

import com.example.android.tmdb.data.api.pogo.MoviePogo
import com.example.android.tmdb.data.db.entity.MovieEntity
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.setBigPosterPath
import com.example.android.tmdb.domain.utill.setSmallPosterPath
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MovieMapperTest {
    private val movieEntity = MovieEntity(
        id = 1L,
        adult = true,
        backdropPath = "backdropPath",
        genreIds = listOf(1, 2, 3),
        originalLanguage = "originalLanguage",
        originalTitle = "originalTitle",
        overview = "overview",
        popularity = 2.2,
        posterPath = "posterPath",
        releaseDate = "releaseDate",
        title = "title",
        video = false,
        voteAverage = 1.1,
        voteCount = 5,
        prevKey = null,
        nextKey = null
    )
    private val movieInfo = MovieInfo(
        id = 1L,
        adult = true,
        backdropPath = "backdropPath",
        genreIds = listOf(1, 2, 3),
        originalLanguage = "originalLanguage",
        originalTitle = "originalTitle",
        overview = "overview",
        popularity = 2.2,
        smallPosterPath = "posterPath".setSmallPosterPath(),
        bigPosterPath = "posterPath".setBigPosterPath(),
        releaseDate = "releaseDate",
        title = "title",
        video = false,
        voteAverage = 1.1,
        voteCount = 5,
        isFavorite = false
    )

    private val moviePogo = MoviePogo(
        id = 1L,
        adult = true,
        backdropPath = "backdropPath",
        genreIds = listOf(1, 2, 3),
        originalLanguage = "originalLanguage",
        originalTitle = "originalTitle",
        overview = "overview",
        popularity = 2.2,
        posterPath = "posterPath",
        releaseDate = "releaseDate",
        title = "title",
        video = false,
        voteAverage = 1.1,
        voteCount = 5
    )

    @Test
    fun testMapMovieEntityToMovieInfo() {
        val mapper = MapMovieEntityToMovieInfo()
        assertEquals(
            mapper.map(movieEntity),
            movieInfo
        )
    }

    @Test
    fun testMapMoviePogoToMovieEntity() {
        val mapper = MapMoviePogoToMovieEntity()
        assertEquals(
            mapper.map(moviePogo),
            movieEntity
        )
    }
}
