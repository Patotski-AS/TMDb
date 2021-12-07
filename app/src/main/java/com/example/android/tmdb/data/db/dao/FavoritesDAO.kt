package com.example.android.tmdb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.tmdb.data.db.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * from favorite_movies WHERE id = :key")
    suspend fun getMovieById(key: Long): FavoriteMovieEntity?

    @Query("DELETE from favorite_movies WHERE id = :key")
    suspend fun deleteFavorite(key: Long)

    @Query("SELECT * FROM favorite_movies ")
    fun getAllMovies(): Flow<List<FavoriteMovieEntity>>
}
