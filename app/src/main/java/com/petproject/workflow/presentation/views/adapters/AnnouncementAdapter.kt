package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemAnnouncementInfoBinding
import com.petproject.workflow.domain.entities.Announcement

class AnnouncementAdapter : ListAdapter<Announcement, AnnouncementAdapter.AnnouncementInfoViewHolder>(AnnouncementDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementInfoViewHolder {
        return AnnouncementInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: AnnouncementInfoViewHolder, position: Int) {
        val announcement = getItem(position)
        holder.bind(announcement)
    }

    class AnnouncementInfoViewHolder(
        val binding: ItemAnnouncementInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object{

            fun inflateFrom(viewGroup: ViewGroup): AnnouncementInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemAnnouncementInfoBinding.inflate(inflater, viewGroup, false)
                return AnnouncementInfoViewHolder(binding)
            }
        }

        fun bind(
            announcement: Announcement
        ) {
            binding.announcement = announcement
        }
    }
}