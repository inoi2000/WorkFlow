package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemTrailerInfoBinding
import com.petproject.workflow.domain.entities.Trailer

class TrailerInfoViewHolder(
    val binding: ItemTrailerInfoBinding,
    private val onTrailerClick: (Trailer) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun inflateFrom(viewGroup: ViewGroup, onTrailerClick: (Trailer) -> Unit): TrailerInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemTrailerInfoBinding.inflate(inflater, viewGroup, false)
            return TrailerInfoViewHolder(binding, onTrailerClick)
        }
    }

    fun bind(trailer: Trailer) {
        binding.trailer = trailer

        binding.root.setOnClickListener {
            onTrailerClick(trailer)
        }

        binding.tvVolume.text = String.format(
            binding.root.context.resources.getString(R.string.volume_pattern),
            trailer.volumeLiter
        )

        binding.tvMaterial.text = String.format(
            binding.root.context.resources.getString(R.string.material_pattern),
            trailer.material
        )
    }
}