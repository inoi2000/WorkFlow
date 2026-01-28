package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemCarInfoBinding
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.entities.CarStatus

class CarInfoViewHolder(
    val binding: ItemCarInfoBinding,
    private val onCarClick: (Car) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun inflateFrom(viewGroup: ViewGroup, onCarClick: (Car) -> Unit): CarInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemCarInfoBinding.inflate(inflater, viewGroup, false)
            return CarInfoViewHolder(binding, onCarClick)
        }
    }

    fun bind(car: Car) {
        binding.car = car

        binding.root.setOnClickListener {
            onCarClick(car)
        }

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

        binding.tvColor.text = car.color

        // Настраиваем статус машины
        binding.chipStatus.text = getCarStatusText(car.status)
        binding.chipStatus.setChipBackgroundColorResource(getCarStatusColor(car.status))

        binding.chipStatus.isClickable = false

        binding.executePendingBindings()
    }

    private fun getCarStatusText(status: CarStatus): String {
        return when (status) {
            CarStatus.ACTIVE -> binding.root.context.getString(R.string.car_status_active)
            CarStatus.MAINTENANCE -> binding.root.context.getString(R.string.car_status_maintenance)
            CarStatus.INACTIVE -> binding.root.context.getString(R.string.car_status_inactive)
        }
    }

    private fun getCarStatusColor(status: CarStatus): Int {
        return when (status) {
            CarStatus.ACTIVE -> R.color.green
            CarStatus.MAINTENANCE -> R.color.orange
            CarStatus.INACTIVE -> R.color.red
        }
    }
}