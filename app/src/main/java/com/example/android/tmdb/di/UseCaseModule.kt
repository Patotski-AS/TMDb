package com.example.android.tmdb.di

import com.example.android.tmdb.domain.repository.FavoriteRepository
import com.example.android.tmdb.domain.repository.MovieRepository
import com.example.android.tmdb.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLocalListMovieUseCase(
        repository: MovieRepository
    ): GetLocalListMovieUseCase {
        return GetLocalListMovieUseCase(repository = repository)
    }

    @Provides
    fun provideLocalMovieInfoUseCase(
        repository: MovieRepository
    ): GetLocalMovieInfoUseCase {
        return GetLocalMovieInfoUseCase(repository = repository)
    }

    @Provides
    fun provideRemoveListSearchResultsUseCase(
        repository: MovieRepository
    ): GetRemoveListSearchResultsUseCase {
        return GetRemoveListSearchResultsUseCase(repository = repository)
    }

    @Provides
    fun provideDeleteLocalFavoriteMovieUseCase(
        repository: FavoriteRepository
    ): DeleteLocalFavoriteMovieUseCase {
        return DeleteLocalFavoriteMovieUseCase(repository = repository)
    }

    @Provides
    fun provideGetLocalFavoriteMovieUseCase(
        repository: FavoriteRepository
    ): GetLocalFavoriteMovieUseCase {
        return GetLocalFavoriteMovieUseCase(repository = repository)
    }

    @Provides
    fun provideGetLocalListFavoriteMoviesUseCase(
        repository: FavoriteRepository
    ): GetLocalListFavoriteMoviesUseCase {
        return GetLocalListFavoriteMoviesUseCase(repository = repository)
    }

    @Provides
    fun provideInsertLocalFavoriteMovieUseCase(
        repository: FavoriteRepository
    ): InsertLocalFavoriteMovieUseCase {
        return InsertLocalFavoriteMovieUseCase(repository = repository)
    }
}