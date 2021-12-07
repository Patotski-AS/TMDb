package com.example.android.tmdb.di

import android.content.Context
import androidx.room.Room
import com.example.android.tmdb.data.db.TMDbDatabase
import com.example.android.tmdb.data.db.dao.MoviesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val NAME_FILE_DB = "movies"

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDao(database: TMDbDatabase): MoviesDAO {
        return database.moviesDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TMDbDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            TMDbDatabase::class.java,
            NAME_FILE_DB
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
