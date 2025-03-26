package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Vacation

object VacationDiffItemCallback : DiffUtil.ItemCallback<Vacation>() {
    override fun areItemsTheSame(oldItem: Vacation, newItem: Vacation): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Vacation, newItem: Vacation): Boolean =
        oldItem == newItem
}