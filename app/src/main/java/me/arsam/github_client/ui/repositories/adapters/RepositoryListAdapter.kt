package me.arsam.github_client.ui.repositories.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.arsam.github_client.RepositoryListQuery
import android.view.LayoutInflater
import me.arsam.github_client.databinding.RepositoryListItemBinding


class RepositoryListAdapter(
    private val repositories: List<RepositoryListQuery.Node?>
) : RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {
    var onEndOfListReached: (() -> Unit)? = null

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
        holder.binding.lblTitle.text = item?.name
        holder.binding.lblDescription.text = item?.description
        holder.binding.lblWatchCount.text = item?.watchers?.totalCount.toString()
        holder.binding.lblStarCount.text = item?.stargazerCount.toString()
        holder.binding.lblForkCount.text = item?.forkCount.toString()

        if (position == repositories.size - 1) {
            onEndOfListReached?.invoke()
        }
    }

    class ViewHolder internal constructor(val binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}