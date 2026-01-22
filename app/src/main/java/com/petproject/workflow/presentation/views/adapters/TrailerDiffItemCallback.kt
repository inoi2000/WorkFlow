package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Trailer

object TrailerDiffItemCallback : DiffUtil.ItemCallback<Trailer>() {
    override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
        oldItem == newItem
}