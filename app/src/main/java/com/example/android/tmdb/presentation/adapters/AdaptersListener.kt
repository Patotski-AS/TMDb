package com.example.android.tmdb.presentation.adapters

import com.example.android.tmdb.domain.models.MovieInfo

interface AdaptersListener {
    fun onClickItem(movie: MovieInfo)
}
