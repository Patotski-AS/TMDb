package com.example.android.tmdb.di

import com.example.android.tmdb.data.mappers.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideMapMovieEntityToMovieInfo(): MapMovieEntityToMovieInfo {
        return MapMovieEntityToMovieInfo()
    }

    @Provides
    fun provideMapMoviePogoToMovieEntity(): MapMoviePogoToMovieEntity {
        return MapMoviePogoToMovieEntity()
    }

    @Provides
    fun provideMapSearchResultsPogoToMovieInfo(): MapSearchResultsPogoToMovieInfo {
        return MapSearchResultsPogoToMovieInfo()
    }

    @Provides
    fun provideMapMovieInfoToFavoriteMovieEntity(): MapMovieInfoToFavoriteMovieEntity {
        return MapMovieInfoToFavoriteMovieEntity()
    }

    @Provides
    fun provideMapFavoriteMovieEntityToMovieInfo(): MapFavoriteMovieEntityToMovieInfo {
        return MapFavoriteMovieEntityToMovieInfo()
    }
}
