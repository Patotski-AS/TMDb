package com.example.android.tmdb.data.repositories

import com.example.android.tmdb.data.db.dao.FavoritesDAO
import com.example.android.tmdb.data.mappers.MapFavoriteMovieEntityToMovieInfo
import com.example.android.tmdb.data.mappers.MapMovieInfoToFavoriteMovieEntity
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val favoritesDAO: FavoritesDAO,
    private val mapMovieInfoToFavoriteMovieEntity: MapMovieInfoToFavoriteMovieEntity,
    private val mapFavoriteMovieEntityToMovieInfo: MapFavoriteMovieEntityToMovieInfo
) : FavoriteRepository {

    override suspend fun insert(movieInfo: MovieInfo) {
        favoritesDAO.insert(mapMovieInfoToFavoriteMovieEntity.map(movieInfo))
    }

    override suspend fun getMovieById(id: Long): MovieInfo? {
        return favoritesDAO.getMovieById(id)?.let { mapFavoriteMovieEntityToMovieInfo.map(it) }
    }

    override suspend fun deleteFavorite(id: Long) {
        favoritesDAO.deleteFavorite(id)
    }

    override fun getAllMovies(): Flow<List<MovieInfo>> {
        return favoritesDAO.getAllMovies()
            .map { listMovies -> listMovies.map { mapFavoriteMovieEntityToMovieInfo.map(it) } }
    }
}
