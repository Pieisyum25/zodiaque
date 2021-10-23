package com.jumpstopstudios.zodiaque.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jumpstopstudios.zodiaque.databinding.LayoutSiteItemBinding
import com.jumpstopstudios.zodiaque.model.Site

class SiteAdapter(
    private val context: Context,
    ) : ListAdapter<Site, SiteAdapter.SiteViewHolder>(DiffCallback) {

    private val viewPool = RecyclerView.RecycledViewPool()

    inner class SiteViewHolder(private var binding: LayoutSiteItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(site: Site){
            binding.siteName.text = site.name
            binding.siteRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = SectionAdapter()
            adapter.submitList(site.sections)
            binding.siteRecyclerview.adapter = adapter
            binding.siteRecyclerview.setRecycledViewPool(viewPool)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Site>() {

        override fun areItemsTheSame(oldItem: Site, newItem: Site): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean = oldItem.name == newItem.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder(LayoutSiteItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}