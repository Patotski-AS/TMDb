package com.example.android.tmdb.di

import android.content.SharedPreferences
import com.example.android.tmdb.data.api.TMDbApiService
import com.example.android.tmdb.data.db.TMDbDatabase
import com.example.android.tmdb.data.mappers.*
import com.example.android.tmdb.data.repositories.FavoriteRepositoryImpl
import com.example.android.tmdb.data.repositories.MovieRepositoryImpl
import com.example.android.tmdb.domain.repository.FavoriteRepository
import com.example.android.tmdb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        database: TMDbDatabase,
        apiService: TMDbApiService,
        mapMoviePogoToMovieEntity: MapMoviePogoToMovieEntity,
        mapMovieEntityToMovieInfo: MapMovieEntityToMovieInfo,
        mapSearchResultsPogoToMovieInfo: MapSearchResultsPogoToMovieInfo,
        preferences: SharedPreferences
    ): MovieRepository {
        return MovieRepositoryImpl(
            database = database,
            apiService = apiService,
            mapMoviePogoToMovieEntity = mapMoviePogoToMovieEntity,
            mapMovieEntityToMovieInfo = mapMovieEntityToMovieInfo,
            mapSearchResultsPogoToMovieInfo = mapSearchResultsPogoToMovieInfo,
            preferences = preferences
        )
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        database: TMDbDatabase,
        mapMovieInfoToFavoriteMovieEntity: MapMovieInfoToFavoriteMovieEntity,
        mapFavoriteMovieEntityToMovieInfo: MapFavoriteMovieEntityToMovieInfo
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            favoritesDAO = database.favoritesDAO(),
            mapMovieInfoToFavoriteMovieEntity = mapMovieInfoToFavoriteMovieEntity,
            mapFavoriteMovieEntityToMovieInfo = mapFavoriteMovieEntityToMovieInfo
        )
    }
}
