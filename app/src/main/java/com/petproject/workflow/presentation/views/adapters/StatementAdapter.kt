package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemStatementInfoBinding
import com.petproject.workflow.domain.entities.JourneyStatus
import com.petproject.workflow.domain.entities.Statement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatementAdapter(
    private val onStatementClick: (String) -> Unit
) : ListAdapter<Statement, StatementAdapter.StatementInfoViewHolder>(StatementDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementInfoViewHolder {
        return StatementInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: StatementInfoViewHolder, position: Int) {
        val statement = getItem(position)
        holder.bind(statement, onStatementClick)
    }

    class StatementInfoViewHolder(
        val binding: ItemStatementInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(viewGroup: ViewGroup): StatementInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemStatementInfoBinding.inflate(inflater, viewGroup, false)
                return StatementInfoViewHolder(binding)
            }
        }

        fun bind(
            statement: Statement,
            onStatementClick: (String) -> Unit
        ) {
            // Простая привязка данных через data binding
            binding.statement = statement

            // Сложные привязки данных вручную
            binding.chipStatus.text = getStatusText(statement.journey?.status ?: JourneyStatus.NEW)
            binding.chipStatus.setChipBackgroundColorResource(getStatusColor(statement.journey?.status ?: JourneyStatus.NEW))

            // Форматирование ID
            binding.textId.text = "Заявка ${formatShortId(statement.id)}"

            // Форматирование дат
            binding.textDestinationTime.text = formatDateTime(statement.destinationTime)
            binding.textCreatedAt.text = "Создана: ${formatDateTime(statement.createdAt)}"
            binding.textUpdatedAt.text = "Обновлена: ${formatDateTime(statement.updatedAt)}"

            // Управление видимостью блоков
            binding.journeyInfoLayout.visibility = if (statement.journey != null) View.VISIBLE else View.GONE
            binding.trailerLayout.visibility = if (statement.journey?.trailer != null) View.VISIBLE else View.GONE

            // Обработчик клика
            binding.root.setOnClickListener {
                onStatementClick(statement.id)
            }
        }

        private fun formatShortId(uuidString: String): String {
            return try {
                val shortId = uuidString.take(8)
                "#$shortId"
            } catch (e: Exception) {
                "#${uuidString}"
            }
        }

        private fun formatDateTime(localDateTime: LocalDateTime): String {
            return try {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                localDateTime.format(formatter)
            } catch (e: Exception) {
                localDateTime.toString()
            }
        }

        private fun getStatusText(status: JourneyStatus): String {
            return when (status) {
                JourneyStatus.NEW -> "НОВАЯ"
                JourneyStatus.CONFIRMED -> "ПОДТВЕРЖДЕНА"
                JourneyStatus.STARTED -> "ВОДИТЕЛЬ В ПУТИ"
                JourneyStatus.FINISHED -> "ЗАВЕРШЕНА"
                JourneyStatus.CANCELED -> "ОТМЕНЕНА"
            }
        }

        private fun getStatusColor(status: JourneyStatus): Int {
            return when (status) {
                JourneyStatus.NEW -> R.color.green
                JourneyStatus.CONFIRMED -> R.color.main_blue
                JourneyStatus.STARTED -> R.color.orange
                JourneyStatus.FINISHED -> R.color.grey
                JourneyStatus.CANCELED -> R.color.red
            }
        }
    }
}