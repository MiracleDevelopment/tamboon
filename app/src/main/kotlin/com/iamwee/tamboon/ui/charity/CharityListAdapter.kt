package com.iamwee.tamboon.ui.charity

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iamwee.tamboon.R
import com.iamwee.tamboon.common.inflate
import com.iamwee.tamboon.data.Charity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_charity_list.*

class CharityListAdapter(
    private val onItemClickListener: (Charity) -> Unit
) : ListAdapter<Charity, CharityViewHolder>(CharityDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityViewHolder {
        return CharityViewHolder(parent inflate R.layout.item_charity_list)
    }

    override fun onBindViewHolder(holder: CharityViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

}

class CharityViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(charity: Charity, onItemClickListener: (Charity) -> Unit) {
        itemTextViewName.text = charity.name
        Glide.with(itemView.context)
            .load(charity.logoUrl)
            .apply(RequestOptions().fitCenter())
            .apply(RequestOptions().placeholder(R.drawable.thumbnail_background))
            .apply(RequestOptions().error(R.drawable.thumbnail_background))
            .into(itemImageViewThumbnail)
        itemView.setOnClickListener { onItemClickListener(charity) }
    }

}

class CharityDiffItemCallback : DiffUtil.ItemCallback<Charity>() {

    override fun areItemsTheSame(oldItem: Charity, newItem: Charity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Charity, newItem: Charity): Boolean {
        return oldItem == newItem
    }
}