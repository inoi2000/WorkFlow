package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.Journey

object JourneyDiffItemCallback : DiffUtil.ItemCallback<Journey>() {
    override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean =
        oldItem.id == newItem.id &&
                oldItem.status == newItem.status &&
                oldItem.statement?.destinationAddress == newItem.statement?.destinationAddress &&
                oldItem.statement?.destinationTime == newItem.statement?.destinationTime &&
                oldItem.car.licensePlate == newItem.car.licensePlate &&
                oldItem.trailer?.licensePlate == newItem.trailer?.licensePlate
}