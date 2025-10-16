package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Statement

object StatementDiffItemCallback  : DiffUtil.ItemCallback<Statement>() {
    override fun areItemsTheSame(oldItem: Statement, newItem: Statement): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Statement, newItem: Statement): Boolean =
        oldItem == newItem
}