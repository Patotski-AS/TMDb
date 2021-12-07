package com.example.android.tmdb.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.findNavController
import com.example.android.tmdb.databinding.FragmentFavoritesBinding
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.presentation.adapters.AdaptersListener
import com.example.android.tmdb.presentation.adapters.FavoriteMoviesAdapter
import com.example.android.tmdb.presentation.vm.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(), AdaptersListener {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    @Inject
    lateinit var moviesAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter.setOnClickListener(this)
        binding.recyclerView.adapter = moviesAdapter
        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.favoriteMovies.collectLatest(moviesAdapter::submitList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(movie: MovieInfo) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionNavFavoritesToNavFavoriteMovieInfoFragment(movie)
        )
    }
}
