package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemAccessInfoBinding
import com.petproject.workflow.domain.entities.Access
import com.petproject.workflow.domain.entities.AccessDurationType
import com.petproject.workflow.domain.entities.DocumentStatus
import java.time.format.DateTimeFormatter

class AccessAdapter : ListAdapter<Access, AccessAdapter.AccessInfoViewHolder>(AccessDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessInfoViewHolder {
        return AccessInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: AccessInfoViewHolder, position: Int) {
        val access = getItem(position)
        holder.bind(access)
    }

    class AccessInfoViewHolder(
        val binding: ItemAccessInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(viewGroup: ViewGroup): AccessInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemAccessInfoBinding.inflate(inflater, viewGroup, false)
                return AccessInfoViewHolder(binding)
            }
        }

        fun bind(access: Access) {
            binding.access = access
            setupViews(access)
            binding.executePendingBindings()
        }

        private fun setupViews(access: Access) {
            val context = binding.root.context

            // Настройка индикатора статуса
            val statusColor = when (access.status) {
                DocumentStatus.VALID -> ContextCompat.getColor(context, R.color.green)
                DocumentStatus.EXPIRING -> ContextCompat.getColor(context, R.color.orange)
                DocumentStatus.EXPIRED -> ContextCompat.getColor(context, R.color.red)
            }
            binding.statusIndicator.setBackgroundColor(statusColor)

            // Настройка Chip статуса
            val (statusText, chipColorRes, textColor) = when (access.status) {
                DocumentStatus.VALID -> Triple(
                    "Активен",
                    R.color.green_light,
                    ContextCompat.getColor(context, R.color.green)
                )
                DocumentStatus.EXPIRING -> Triple(
                    "Истекает",
                    R.color.orange_light,
                    ContextCompat.getColor(context, R.color.orange)
                )
                DocumentStatus.EXPIRED -> Triple(
                    "Просрочен",
                    R.color.red_light,
                    ContextCompat.getColor(context, R.color.red)
                )
            }

            binding.statusChip.text = statusText
            binding.statusChip.setChipBackgroundColorResource(chipColorRes) // Используем ID ресурса
            binding.statusChip.setTextColor(textColor)

            // Настройка типа допуска
            val typeText = when (access.type) {
                AccessDurationType.ONETIME -> "Одноразовый"
                AccessDurationType.TEMPORARY -> "Временный"
                AccessDurationType.PERMANENT -> "Постоянный"
            }
            binding.accessType.text = typeText

            // Настройка срока действия
            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

            access.validUntil?.let {
                binding.validUntil.text = "Действует до: ${it.format(dateFormatter)}"
            } ?: run {
                binding.validUntil.text = "Бессрочный"
            }

            // Настройка дат
            binding.createdAt.text = "Создан: ${access.createdAt.format(timeFormatter)}"
            binding.updatedAt.text = "Обновлен: ${access.updatedAt.format(timeFormatter)}"
        }
    }
}