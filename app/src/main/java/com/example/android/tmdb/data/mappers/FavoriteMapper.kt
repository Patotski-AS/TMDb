package com.example.android.tmdb.data.mappers

import com.example.android.tmdb.data.db.entity.FavoriteMovieEntity
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.Mapper

class MapFavoriteMovieEntityToMovieInfo : Mapper<FavoriteMovieEntity, MovieInfo> {
    override fun map(from: FavoriteMovieEntity): MovieInfo {
        return MovieInfo(
            id = from.id,
            adult = from.adult,
            backdropPath = from.backdropPath,
            genreIds = from.genreIds,
            originalLanguage = from.originalLanguage,
            originalTitle = from.originalTitle,
            overview = from.overview,
            popularity = from.popularity,
            smallPosterPath = from.smallPosterPath,
            bigPosterPath = from.bigPosterPath,
            releaseDate = from.releaseDate,
            title = from.title,
            video = from.video,
            voteAverage = from.voteAverage,
            voteCount = from.voteCount,
            isFavorite = from.isFavorite
        )
    }
}

class MapMovieInfoToFavoriteMovieEntity : Mapper<MovieInfo, FavoriteMovieEntity> {
    override fun map(from: MovieInfo): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            id = from.id,
            adult = from.adult,
            backdropPath = from.backdropPath,
            genreIds = from.genreIds,
            originalLanguage = from.originalLanguage,
            originalTitle = from.originalTitle,
            overview = from.overview,
            popularity = from.popularity,
            smallPosterPath = from.smallPosterPath,
            bigPosterPath = from.bigPosterPath,
            releaseDate = from.releaseDate,
            title = from.title,
            video = from.video,
            voteAverage = from.voteAverage,
            voteCount = from.voteCount,
            isFavorite = from.isFavorite
        )
    }
}
