package com.example.android.tmdb.data.mappers

import com.example.android.tmdb.data.api.pogo.SearchResultsPogo
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.Mapper
import com.example.android.tmdb.domain.utill.setBigPosterPath
import com.example.android.tmdb.domain.utill.setSmallPosterPath

class MapSearchResultsPogoToMovieInfo : Mapper<SearchResultsPogo, MovieInfo> {
    override fun map(from: SearchResultsPogo): MovieInfo {
        return MovieInfo(
            id = from.id,
            adult = from.adult,
            backdropPath = from.backdropPath,
            genreIds = from.genreIds,
            originalLanguage = from.originalLanguage,
            originalTitle = from.originalTitle,
            overview = from.overview,
            popularity = from.popularity,
            smallPosterPath = from.posterPath.setSmallPosterPath(),
            bigPosterPath = from.posterPath.setBigPosterPath(),
            releaseDate = from.releaseDate,
            title = from.title,
            video = from.video,
            voteAverage = from.voteAverage,
            voteCount = from.voteCount
        )
    }
}
