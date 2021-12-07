package com.example.android.tmdb.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieInfo(
    val id: Long = -1,
    val adult: Boolean? = false,
    val backdropPath: String? = "",
    val genreIds: List<Int>? = emptyList(),
    val originalLanguage: String? = "",
    val originalTitle: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val smallPosterPath: String? = "",
    val bigPosterPath: String? = "",
    val releaseDate: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0,
    var isFavorite: Boolean = false
) : Parcelable
