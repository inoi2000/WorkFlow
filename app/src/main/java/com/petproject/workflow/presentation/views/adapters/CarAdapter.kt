package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.Car

class CarAdapter(
    private val onCarClick: (Car) -> Unit
) : ListAdapter<Car, CarInfoViewHolder>(CarDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarInfoViewHolder =
        CarInfoViewHolder.inflateFrom(parent, onCarClick)

    override fun onBindViewHolder(holder: CarInfoViewHolder, position: Int) {
        val car = getItem(position)
        holder.bind(car)
    }
}