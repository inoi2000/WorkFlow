package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemFuellingInfoBinding
import com.petproject.workflow.domain.entities.Fuelling

class FuellingAdapter :
    ListAdapter<Fuelling, FuellingAdapter.FuellingInfoViewHolder>(FuellingDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuellingInfoViewHolder {
        return FuellingInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: FuellingInfoViewHolder, position: Int) {
        val fuelling = getItem(position)
        holder.bind(fuelling)
    }

    class FuellingInfoViewHolder(
        val binding: ItemFuellingInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {

            fun inflateFrom(viewGroup: ViewGroup): FuellingInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemFuellingInfoBinding.inflate(inflater, viewGroup, false)
                return FuellingInfoViewHolder(binding)
            }
        }

        fun bind(
            fuelling: Fuelling
        ) {
            binding.fuelling = fuelling
        }
    }
}