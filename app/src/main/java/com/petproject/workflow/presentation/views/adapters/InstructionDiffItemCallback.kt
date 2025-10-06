package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Instruction

object InstructionDiffItemCallback : DiffUtil.ItemCallback<Instruction>() {
    override fun areItemsTheSame(oldItem: Instruction, newItem: Instruction): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Instruction, newItem: Instruction): Boolean =
        oldItem == newItem
}