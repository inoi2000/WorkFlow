package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Storage

object StorageDiffItemCallback : DiffUtil.ItemCallback<Storage>() {

    override fun areItemsTheSame(oldItem: Storage, newItem: Storage): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Storage, newItem: Storage): Boolean =
        oldItem == newItem
}