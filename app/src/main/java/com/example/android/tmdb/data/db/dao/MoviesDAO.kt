package com.example.android.tmdb.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.tmdb.data.db.entity.MovieEntity

@Dao
interface MoviesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: List<MovieEntity>)

    @Query("SELECT * from movies WHERE id = :key")
    suspend fun getMovieById(key: Long): MovieEntity?

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Query("SELECT * FROM movies ")
    fun getAllMovies(): PagingSource<Int, MovieEntity>
}
