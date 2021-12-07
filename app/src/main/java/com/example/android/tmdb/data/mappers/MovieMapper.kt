package com.example.android.tmdb.data.mappers

import com.example.android.tmdb.data.api.pogo.MoviePogo
import com.example.android.tmdb.data.db.entity.MovieEntity
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.Mapper
import com.example.android.tmdb.domain.utill.setBigPosterPath
import com.example.android.tmdb.domain.utill.setSmallPosterPath

class MapMovieEntityToMovieInfo : Mapper<MovieEntity, MovieInfo> {
    override fun map(from: MovieEntity): MovieInfo {
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
            voteCount = from.voteCount,
            isFavorite = from.isFavorite
        )
    }
}

class MapMoviePogoToMovieEntity : Mapper<MoviePogo, MovieEntity> {
    override fun map(from: MoviePogo): MovieEntity {
        return MovieEntity(
            id = from.id,
            adult = from.adult,
            backdropPath = from.backdropPath,
            genreIds = from.genreIds,
            originalLanguage = from.originalLanguage,
            originalTitle = from.originalTitle,
            overview = from.overview,
            popularity = from.popularity,
            posterPath = from.posterPath,
            releaseDate = from.releaseDate,
            title = from.title,
            video = from.video,
            voteAverage = from.voteAverage,
            voteCount = from.voteCount,
            prevKey = null,
            nextKey = null
        )
    }
}
