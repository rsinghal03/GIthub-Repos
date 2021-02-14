package com.example.githubrepos.ui.repository.repositorylist

import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepos.R
import com.example.githubrepos.databinding.RepositoryListItemBinding
import com.example.githubrepos.model.remote.repositoryresponse.Item

class RepositoryListViewHolder(
    private val repositoryListItemBinding: RepositoryListItemBinding,
    private val itemClick: ((item: Item?) -> Unit)?
) : RecyclerView.ViewHolder(repositoryListItemBinding.root) {


    fun bind(item: Item?) {
        val resource = this.itemView.context.resources
        repositoryListItemBinding.run {
            repoName.text = item?.name
            repoDescription.text = item?.description
            repoForks.text = item?.forks.toString()
            repoStars.text = item?.stargazers_count.toString()
            repoLanguage.text = resource.getString(R.string.language, item?.language)
        }
        bindClickListener(item)
    }

    private fun bindClickListener(item: Item?) {
        this.itemView.setOnClickListener {
            itemClick?.invoke(item)
        }
    }
}