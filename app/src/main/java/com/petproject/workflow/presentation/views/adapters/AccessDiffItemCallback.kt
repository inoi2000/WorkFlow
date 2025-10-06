package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Access

object AccessDiffItemCallback : DiffUtil.ItemCallback<Access>() {
    override fun areItemsTheSame(oldItem: Access, newItem: Access): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Access, newItem: Access): Boolean =
        oldItem == newItem
}