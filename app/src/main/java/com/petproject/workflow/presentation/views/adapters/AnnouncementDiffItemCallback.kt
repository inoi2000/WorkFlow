package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Announcement

object AnnouncementDiffItemCallback : DiffUtil.ItemCallback<Announcement>() {
    override fun areItemsTheSame(oldItem: Announcement, newItem: Announcement): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Announcement, newItem: Announcement): Boolean =
        oldItem == newItem
}