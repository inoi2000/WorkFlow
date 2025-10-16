package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemJourneyInfoBinding
import com.petproject.workflow.domain.entities.Journey

class JourneyAdapter :
    ListAdapter<Journey, JourneyAdapter.JourneyInfoViewHolder>(JourneyDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyInfoViewHolder {
        return JourneyInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: JourneyInfoViewHolder, position: Int) {
        val journey = getItem(position)
        holder.bind(journey)
    }

    class JourneyInfoViewHolder(
        val binding: ItemJourneyInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {

            fun inflateFrom(viewGroup: ViewGroup): JourneyInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemJourneyInfoBinding.inflate(inflater, viewGroup, false)
                return JourneyInfoViewHolder(binding)
            }
        }

        fun bind(
            journey: Journey
        ) {
            binding.journey = journey
        }
    }
}