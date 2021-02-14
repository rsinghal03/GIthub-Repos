package com.example.githubrepos.ui.repository.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepos.databinding.NetworkStateItemBinding

class RepositoryListLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RepositoryListLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class LoadStateViewHolder(private val networkStateItemBinding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(networkStateItemBinding.root) {

        fun bind(loadState: LoadState) {
            val progress = networkStateItemBinding.progressBar
            val btnRetry = networkStateItemBinding.retryButton
            val txtErrorMessage = networkStateItemBinding.errorMsg

            progress.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState !is LoadState.Loading
            txtErrorMessage.isVisible = loadState !is LoadState.Loading

            if (loadState is LoadState.Error) {
                txtErrorMessage.text = loadState.error.localizedMessage
            }

            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}