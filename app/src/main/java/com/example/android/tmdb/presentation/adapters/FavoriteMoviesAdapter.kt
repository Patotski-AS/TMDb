package com.example.android.tmdb.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.android.tmdb.R
import com.example.android.tmdb.databinding.ItemMovieBinding
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.getGenreString
import com.example.android.tmdb.presentation.utill.setFavoritePicture
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FavoriteMoviesAdapter @Inject constructor(
    private val glide: RequestManager,
    @ActivityContext private val context: Context
) : ListAdapter<MovieInfo, FavoriteMoviesAdapter.FavoriteMoviesViewHolder>(MoviesDiffCallback) {
    private var listener: AdaptersListener? = null
    fun setOnClickListener(onClickListener: AdaptersListener) {
        this.listener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return FavoriteMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        holder.bind(getItem(holder.bindingAdapterPosition))
    }

    private object MoviesDiffCallback : DiffUtil.ItemCallback<MovieInfo>() {

        override fun areItemsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean {
            return oldItem == newItem
        }
    }

    inner class FavoriteMoviesViewHolder(
        private val binding: ItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieInfo: MovieInfo?) {
            glide.load(movieInfo?.smallPosterPath)
                .fitCenter()
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.imageViewSmallPoster)

            binding.apply {
                movieInfo?.let { info ->
                    imageIsFavorite.isGone = true
                    textViewTitle.text = info.title ?: ""
                    tvValueGenre.text =
                        info.genreIds?.getGenreString(context.applicationContext) ?: ""
                    tvValueReleaseDate.text = info.releaseDate
                    "${info.voteAverage.toString()}${context.getString(R.string.text_average)}"
                        .also { tvValueVoteAverage.text = it }
                }
            }
            initButtonsListeners(movieInfo)
        }

        private fun initButtonsListeners(movie: MovieInfo?) {
            binding.cardItem.setOnClickListener {
                movie?.let { movie -> listener?.onClickItem(movie) }
            }
        }
    }
}