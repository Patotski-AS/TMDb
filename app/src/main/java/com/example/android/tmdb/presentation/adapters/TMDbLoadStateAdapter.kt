package com.example.android.tmdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tmdb.R
import com.example.android.tmdb.databinding.ItemErrorBinding

class TMDbLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<TMDbLoadStateAdapter.TMDbLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: TMDbLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): TMDbLoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemErrorBinding.inflate(layoutInflater, parent, false)
        return TMDbLoadStateViewHolder(binding)
    }

    inner class TMDbLoadStateViewHolder(
        private val binding: ItemErrorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState == LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
            }
            initButtonsListeners()
        }

        private fun initButtonsListeners() {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }
    }
}


