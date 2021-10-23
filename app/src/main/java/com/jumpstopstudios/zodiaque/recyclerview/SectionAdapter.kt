package com.jumpstopstudios.zodiaque.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jumpstopstudios.zodiaque.databinding.LayoutSectionItemBinding
import com.jumpstopstudios.zodiaque.model.Section

/**
 * ListAdapter vs RecyclerView.Adapter:
 * - ListAdapter has better performance by default for Lists that change.
 *
 * - ListAdapter extends RecyclerView.Adapter.
 * - ListAdapter computes differences between Lists on a background thread with AsyncListDiff.
 * - RecyclerView.Adapter can be configured to work the same way.
 * - ListAdapter forces the use of a DiffUtil callback.
 */
class SectionAdapter : ListAdapter<Section, SectionAdapter.SectionViewHolder>(DiffCallback) {

    class SectionViewHolder(private var binding: LayoutSectionItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(section: Section){
            binding.sectionContent.text = section.content
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Section>() {

        override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean = oldItem.content == newItem.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(LayoutSectionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}