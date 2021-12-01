package me.arsam.github_client.ui.repositories.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import me.arsam.github_client.data.models.Repository
import me.arsam.github_client.databinding.RepositoryListItemBinding
import me.arsam.github_client.ui.base.adapter.BaseAdapter

class RepositoryListAdapter(
    diffCallback: DiffUtil.ItemCallback<Repository>
) : BaseAdapter<RepositoryListItemBinding, Repository>(diffCallback) {

    override fun setupViewBinding(parent: ViewGroup): RepositoryListItemBinding {
        return RepositoryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
    }

    override fun bind(binding: RepositoryListItemBinding, item: Repository) {
        binding.apply {
            titleTextView.text = item.name
            descriptionTextView.text = item.description
            watchCountTextView.text = item.watchersCount.toString()
            starCountTextView.text = item.starsCount.toString()
            forkCountTextView.text = item.forksCount.toString()
        }
    }
}