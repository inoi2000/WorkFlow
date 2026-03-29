package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemFuellingInfoBinding
import com.petproject.workflow.domain.entities.Fuelling
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FuellingAdapter :
    ListAdapter<Fuelling, FuellingAdapter.FuellingInfoViewHolder>(FuellingDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuellingInfoViewHolder {
        return FuellingInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: FuellingInfoViewHolder, position: Int) {
        val fuelling = getItem(position)
        holder.bind(fuelling)
    }

    class FuellingInfoViewHolder(
        val binding: ItemFuellingInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(viewGroup: ViewGroup): FuellingInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemFuellingInfoBinding.inflate(inflater, viewGroup, false)
                return FuellingInfoViewHolder(binding)
            }
        }

        fun bind(fuelling: Fuelling) {
            binding.fuelling = fuelling

            // Устанавливаем время и дату вручную (поля не привязаны через Data Binding)
            binding.textTime.text = formatTime(fuelling.createdAt)
            binding.textDate.text = formatDate(fuelling.createdAt)

            binding.cardFuellingItem.setOnClickListener {

            }

            binding.executePendingBindings()
        }

        private fun formatTime(dateTime: LocalDateTime?): String {
            return if (dateTime == null) "" else dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }

        private fun formatDate(dateTime: LocalDateTime?): String {
            return if (dateTime == null) "" else dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        }
    }
}