package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Task

object TaskDiffItemCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem == newItem
}