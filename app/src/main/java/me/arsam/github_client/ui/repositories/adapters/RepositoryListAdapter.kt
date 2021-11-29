package me.arsam.github_client.ui.repositories.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.arsam.github_client.RepositoryListQuery
import android.view.LayoutInflater
import me.arsam.github_client.data.models.Repository
import me.arsam.github_client.databinding.RepositoryListItemBinding


class RepositoryListAdapter(
    var repositories: List<Repository>
) : RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            RepositoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repositories[position]
        holder.binding.txtTitle.text = item.name
        holder.binding.txtDescription.text = item.description
        holder.binding.txtWatchCount.text = item.watchersCount.toString()
        holder.binding.txtStarCount.text = item.starsCount.toString()
        holder.binding.txtForkCount.text = item.forksCount.toString()
    }

    class ViewHolder internal constructor(val binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}