package com.example.githubrepos.ui.repository.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.githubrepos.R
import com.example.githubrepos.databinding.RepositoryListItemBinding
import com.example.githubrepos.model.remote.repositoryresponse.Item

class RepositoryListAdapter : PagingDataAdapter<Item, RepositoryListViewHolder>(ITEM_COMPARATOR) {

    var itemClickListener: ((item: Item?) -> Unit)? = null

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder {
        val binding: RepositoryListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.repository_list_item,
            parent,
            false
        )
        return RepositoryListViewHolder(binding, itemClickListener)
    }

    companion object {
        val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id
        }
    }

}