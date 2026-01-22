package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Car

object CarDiffItemCallback : DiffUtil.ItemCallback<Car>() {
    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean =
        oldItem == newItem
}