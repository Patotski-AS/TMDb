package com.example.android.tmdb.presentation.utill

import android.widget.ImageView
import com.example.android.tmdb.R

fun ImageView.setFavoritePicture(isFavorite: Boolean) {
    when (isFavorite) {
        true -> this.setImageResource(R.drawable.ic_baseline_star_rate_24_2)
        false -> this.setImageResource(R.drawable.ic_baseline_star_rate_24)
    }
}
