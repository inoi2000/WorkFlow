package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Comment

object CommentDiffItemCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
        oldItem == newItem
}