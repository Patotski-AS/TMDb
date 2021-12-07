package com.example.android.tmdb.domain.utill

import android.content.Context
import com.example.android.tmdb.R

private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/"
private const val BIG_POSTER_SIZE = "w780"
private const val SMALL_POSTER_SIZE = "w185"

fun String?.setSmallPosterPath() = when (this) {
    null -> null
    else -> "$BASE_POSTER_URL$SMALL_POSTER_SIZE$this"
}

fun String?.setBigPosterPath() = when (this) {
    null -> null
    else -> "$BASE_POSTER_URL$BIG_POSTER_SIZE$this"
}

fun List<Int>.getGenreString(context: Context): String {
    val builder = StringBuilder("")
    this.forEach { builder.append("'${getGenre(it, context)}' ") }
    return builder.toString()
}

fun getGenre(int: Int, context: Context): String {
    return when (int) {
        28 -> context.getString(R.string.genre_action)
        12 -> context.getString(R.string.genre_adventure)
        16 -> context.getString(R.string.genre_animation)
        35 -> context.getString(R.string.genre_comedy)
        80 -> context.getString(R.string.genre_crime)
        99 -> context.getString(R.string.genre_documentary)
        18 -> context.getString(R.string.genre_drama)
        10751 -> context.getString(R.string.genre_family)
        14 -> context.getString(R.string.genre_fantasy)
        36 -> context.getString(R.string.genre_history)
        27 -> context.getString(R.string.genre_horror)
        10402 -> context.getString(R.string.genre_music)
        9648 -> context.getString(R.string.genre_mystery)
        10749 -> context.getString(R.string.genre_romance)
        878 -> context.getString(R.string.genre_science_fiction)
        10770 -> context.getString(R.string.genre_tv_movie)
        53 -> context.getString(R.string.genre_thriller)
        10752 -> context.getString(R.string.genre_war)
        37 -> context.getString(R.string.genre_western)
        else -> context.getString(R.string.no_info)
    }
}
