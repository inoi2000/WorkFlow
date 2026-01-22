package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemCarInfoBinding
import com.petproject.workflow.domain.entities.Car

class CarInfoViewHolder(
    val binding: ItemCarInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun inflateFrom(viewGroup: ViewGroup): CarInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemCarInfoBinding.inflate(inflater, viewGroup, false)
            return CarInfoViewHolder(binding)
        }
    }

    fun bind(car: Car) {
        binding.car = car

        binding.tvYear.text = String.format(
            binding.root.context.resources.getString(R.string.year_pattern),
            car.year
        )

        binding.tvVin.text = String.format(
            binding.root.context.resources.getString(R.string.vin_pattern),
            car.vin
        )

        binding.tvOdometer.text = String.format(
            binding.root.context.resources.getString(R.string.odometer_pattern),
            car.odometer
        )
    }
}