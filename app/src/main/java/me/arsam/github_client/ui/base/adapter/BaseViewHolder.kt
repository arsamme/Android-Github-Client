package me.arsam.github_client.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<ViewBindingType : ViewBinding>(val binder: ViewBindingType) :
    RecyclerView.ViewHolder(binder.root) {
}