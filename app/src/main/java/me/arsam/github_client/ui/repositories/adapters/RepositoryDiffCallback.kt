package me.arsam.github_client.ui.repositories.adapters

import androidx.recyclerview.widget.DiffUtil
import me.arsam.github_client.data.models.Repository

class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}