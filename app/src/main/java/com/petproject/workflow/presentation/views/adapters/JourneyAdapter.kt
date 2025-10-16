package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemJourneyInfoBinding
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.entities.JourneyStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JourneyAdapter(
    private val onJourneyClick: (String) -> Unit
) : ListAdapter<Journey, JourneyAdapter.JourneyInfoViewHolder>(JourneyDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyInfoViewHolder {
        return JourneyInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: JourneyInfoViewHolder, position: Int) {
        val journey = getItem(position)
        holder.bind(journey, onJourneyClick)
    }

    class JourneyInfoViewHolder(
        val binding: ItemJourneyInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(viewGroup: ViewGroup): JourneyInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemJourneyInfoBinding.inflate(inflater, viewGroup, false)
                return JourneyInfoViewHolder(binding)
            }
        }

        fun bind(journey: Journey, onJourneyClick: (String) -> Unit) {
            binding.journey = journey

            // Устанавливаем данные напрямую через binding
            binding.textDay.text = formatDay(journey.statement?.destinationTime)
            binding.textDateTime.text = formatDateTime(journey.statement?.destinationTime)
            binding.textPhone.text = formatPhone(journey.statement?.contactPhone)
            binding.textAdditionalInfo.text = getAdditionalInfo(journey)

            // Настраиваем статус
            binding.chipStatus.text = getStatusText(journey.status)
            binding.chipStatus.setChipBackgroundColorResource(getStatusColor(journey.status))

            // Настраиваем видимость прицепа
            binding.rowTrailer.visibility = if (journey.trailer != null) View.VISIBLE else View.GONE

            // Обработчики кликов
            binding.cardJourneyItem.setOnClickListener {
                onJourneyClick(journey.id)
            }

            binding.buttonDetails.setOnClickListener {
                onJourneyClick(journey.id)
            }

            binding.executePendingBindings()
        }

        private fun formatDay(destinationTime: LocalDateTime?): String {
            return if (destinationTime == null) {
                " - "
            } else {
                val today = LocalDate.now()
                val destinationDate = destinationTime.toLocalDate()
                when (destinationDate) {
                    today -> "СЕГОДНЯ"
                    today.plusDays(1) -> "ЗАВТРА"
                    else -> destinationTime.format(DateTimeFormatter.ofPattern("EEE")).uppercase()
                }
            }
        }

        private fun formatDateTime(destinationTime: LocalDateTime?): String {
            return if (destinationTime == null) {
                " - "
            } else {
                destinationTime.format(DateTimeFormatter.ofPattern("HH:mm • dd MMM"))
            }
        }

        private fun formatPhone(phone: String?): String {
            return if (phone == null) {
                " - "
            } else if (phone.length == 11 && phone.startsWith("7")) {
                "+7 ${phone.substring(1, 4)} ${phone.substring(4, 7)}-${
                    phone.substring(
                        7,
                        9
                    )
                }-${phone.substring(9)}"
            } else {
                phone
            }
        }

        private fun getAdditionalInfo(journey: Journey): String {
            val created = journey.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            return "Создано: $created"
        }

        private fun getStatusText(status: JourneyStatus): String {
            return when (status) {
                JourneyStatus.NEW -> "НОВЫЙ"
                JourneyStatus.CONFIRMED -> "ПОДТВЕРЖДЕН"
                JourneyStatus.STARTED -> "В ПУТИ"
                JourneyStatus.FINISHED -> "ЗАВЕРШЕН"
                JourneyStatus.CANCELED -> "ОТМЕНЕН"
            }
        }

        private fun getStatusColor(status: JourneyStatus): Int {
            return when (status) {
                JourneyStatus.NEW -> R.color.grey
                JourneyStatus.CONFIRMED -> R.color.main_blue
                JourneyStatus.STARTED -> R.color.orange
                JourneyStatus.FINISHED -> R.color.green
                JourneyStatus.CANCELED -> R.color.red
            }
        }
    }
}