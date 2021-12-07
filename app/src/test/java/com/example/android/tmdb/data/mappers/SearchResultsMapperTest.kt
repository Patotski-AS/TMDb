package com.example.android.tmdb.data.mappers

import com.example.android.tmdb.data.api.pogo.SearchResultsPogo
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.setBigPosterPath
import com.example.android.tmdb.domain.utill.setSmallPosterPath
import junit.framework.TestCase
import org.junit.Test

class SearchResultsMapperTest {

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

    private val searchResultsPogo = SearchResultsPogo(
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
    fun testMapSearchResultsPogoToMovieInfo() {
        val mapper = MapSearchResultsPogoToMovieInfo()
        TestCase.assertEquals(
            mapper.map(searchResultsPogo),
            movieInfo
        )
    }

}