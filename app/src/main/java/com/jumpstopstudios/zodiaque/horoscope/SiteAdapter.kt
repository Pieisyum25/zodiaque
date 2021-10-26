package com.jumpstopstudios.zodiaque.horoscope

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jumpstopstudios.zodiaque.databinding.LayoutSiteItemBinding
import com.jumpstopstudios.zodiaque.model.Site

class SiteAdapter(
    private val sites: List<Site>,
    private val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.Adapter<SiteAdapter.SiteViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    
    inner class SiteViewHolder(private var binding: LayoutSiteItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(site: Site){
            binding.apply{
                siteName.text = site.name
                siteRecyclerview.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = SectionAdapter(site.sections, lifecycleOwner)
                    setRecycledViewPool(viewPool)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder(LayoutSiteItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(sites[position])
    }

    override fun getItemCount(): Int = sites.size
}