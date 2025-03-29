package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Absence

object AbsenceDiffItemCallback : DiffUtil.ItemCallback<Absence>() {
    override fun areItemsTheSame(oldItem: Absence, newItem: Absence): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Absence, newItem: Absence): Boolean =
        oldItem == newItem
}