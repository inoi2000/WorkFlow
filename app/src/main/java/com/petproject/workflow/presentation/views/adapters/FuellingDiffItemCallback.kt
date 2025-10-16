package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Fuelling

object FuellingDiffItemCallback : DiffUtil.ItemCallback<Fuelling>() {
    override fun areItemsTheSame(oldItem: Fuelling, newItem: Fuelling): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Fuelling, newItem: Fuelling): Boolean =
        oldItem == newItem
}