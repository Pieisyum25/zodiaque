package com.jumpstopstudios.zodiaque.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jumpstopstudios.zodiaque.TAG
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
        override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean {
            if (oldItem.sections.size != newItem.sections.size) return false
            for (index in oldItem.sections.indices){
                if (oldItem.sections[index].content != newItem.sections[index].content) return false
                Log.d(TAG, oldItem.sections[index].content)
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder(LayoutSiteItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Force DiffUtil to check for differences.
     * You must submit a different List instance for it to even consider deep changes.
     */
    override fun submitList(list: List<Site>?) {
        super.submitList(list?.let { ArrayList(it.map{ site -> site.copy() }) })
    }
}