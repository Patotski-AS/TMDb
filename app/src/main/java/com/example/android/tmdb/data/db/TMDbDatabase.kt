package com.example.android.tmdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.tmdb.data.db.dao.FavoritesDAO
import com.example.android.tmdb.data.db.dao.MoviesDAO
import com.example.android.tmdb.data.db.entity.FavoriteMovieEntity
import com.example.android.tmdb.data.db.entity.MovieEntity
import com.example.android.tmdb.data.utill.Converters

@Database(
    entities = [MovieEntity::class, FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TMDbDatabase : RoomDatabase() {
    abstract fun moviesDAO(): MoviesDAO
    abstract fun favoritesDAO(): FavoritesDAO
}
