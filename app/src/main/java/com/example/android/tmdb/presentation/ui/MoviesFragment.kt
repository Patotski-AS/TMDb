package com.example.android.tmdb.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.android.tmdb.R
import com.example.android.tmdb.databinding.FragmentMoviesBinding
import com.example.android.tmdb.domain.models.MovieInfo
import com.example.android.tmdb.presentation.adapters.AdaptersListener
import com.example.android.tmdb.presentation.adapters.MoviesAdapter
import com.example.android.tmdb.presentation.adapters.TMDbLoadStateAdapter
import com.example.android.tmdb.presentation.vm.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(), AdaptersListener {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = moviesAdapter
                .withLoadStateFooter(
                    footer = TMDbLoadStateAdapter { moviesAdapter.retry() }
                )
            searchView.doAfterTextChanged { text -> viewModel.setQuery(text?.toString() ?: "") }

            swipeRefreshLayout.setOnRefreshListener {
                moviesAdapter.retry()
                swipeRefreshLayout.isRefreshing = false
            }
        }

        moviesAdapter.setOnClickListener(this)

        with(viewModel) {
            addRepeatingJob(Lifecycle.State.STARTED) {
                searchResults.collectLatest(moviesAdapter::submitData)
            }

            query.flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .onEach(::updateSearchQuery)
                .launchIn(lifecycleScope)

            lifecycleScope.launch {
                favoriteMovies.collectLatest() { moviesAdapter.refresh() }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            moviesAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Error) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.no_internet),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                binding.progressBar.isVisible = loadStates.refresh == LoadState.Loading
            }
        }
    }

    private fun updateSearchQuery(searchQuery: String) {
        with(binding.searchView) {
            if ((text?.toString() ?: "") != searchQuery) {
                setText(searchQuery)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(movie: MovieInfo) {
        findNavController().navigate(MoviesFragmentDirections.actionNavMovieToNavMovieInfo(movie))
    }
}
