package me.arsam.github_client.ui.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<ViewBindingType : ViewBinding, T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<ViewBindingType>>(diffCallback) {

    abstract fun setupViewBinding(parent: ViewGroup): ViewBindingType
    abstract fun bind(binding: ViewBindingType, item: T)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBindingType> {
        val binder = setupViewBinding(parent)
        return BaseViewHolder(binder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBindingType>, position: Int) {
        bind(holder.binder, currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}