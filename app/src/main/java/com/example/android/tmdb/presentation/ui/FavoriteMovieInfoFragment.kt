package com.example.android.tmdb.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import com.bumptech.glide.RequestManager
import com.example.android.tmdb.R
import com.example.android.tmdb.databinding.FragmentFavoriteMovieInfoBinding
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.domain.utill.getGenreString
import com.example.android.tmdb.presentation.vm.FavoriteMovieInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMovieInfoFragment : Fragment() {
    private var _binding: FragmentFavoriteMovieInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteMovieInfoViewModel by viewModels()
    private val movie by lazy { FavoriteMovieInfoFragmentArgs.fromBundle(requireArguments()).movie }

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            setMovieInfo(movie)
            addRepeatingJob(Lifecycle.State.STARTED) { movieInfo.collectLatest(::setInfo) }
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }

            imageFavorite.setOnClickListener {
                imageFavorite.isGone = true
                imageNoFavorite.isVisible = true
                viewModel.deleteFavoriteMovie()
            }
        }
    }

    private fun setInfo(movieInfo: MovieInfo) {
        with(binding) {
            toolbar.subtitle = movieInfo.title ?: ""
            glide.load(movieInfo.bigPosterPath)
                .error(movieInfo.smallPosterPath ?: R.drawable.ic_baseline_broken_image_24)
                .into(binding.ivBigPoster)
            tvValueTitle.text = movieInfo.title ?: ""
            tvValueGenre.text =
                movieInfo.genreIds?.getGenreString(requireContext().applicationContext) ?: ""
            tvValueReleaseDate.text = movieInfo.releaseDate ?: ""
            tvValueVoteAverage.text = movieInfo.voteAverage.toString()
            tvValueOverview.text = movieInfo.overview ?: ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
