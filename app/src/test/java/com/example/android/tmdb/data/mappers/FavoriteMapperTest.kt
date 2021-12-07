package com.example.android.tmdb.data.mappers

import com.example.android.tmdb.data.db.entity.FavoriteMovieEntity
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.setBigPosterPath
import com.example.android.tmdb.domain.utill.setSmallPosterPath
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class FavoriteMapperTest {
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

    private val favoriteMovieEntity = FavoriteMovieEntity(
        idDb = 0L,
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

    @Test
    fun testMapFavoriteMovieEntityToMovieInfo() {
        val mapper = MapFavoriteMovieEntityToMovieInfo()
        assertThat(mapper.map(favoriteMovieEntity), `is`(movieInfo))
    }

    @Test
    fun testMapMovieInfoToFavoriteMovieEntity() {
        val mapper = MapMovieInfoToFavoriteMovieEntity()
        assertThat(mapper.map(movieInfo), `is`(favoriteMovieEntity))
    }
}