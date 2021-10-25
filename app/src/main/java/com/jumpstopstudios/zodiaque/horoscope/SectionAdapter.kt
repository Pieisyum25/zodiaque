package com.jumpstopstudios.zodiaque.horoscope

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
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
class SectionAdapter(
    private val sections: List<Section>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {


    inner class SectionViewHolder(private var binding: LayoutSectionItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(section: Section){
            binding.sectionTitle.text = section.title
            section.content.observe(lifecycleOwner) { content ->
                binding.sectionContent.text = content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(LayoutSectionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount(): Int = sections.size
}