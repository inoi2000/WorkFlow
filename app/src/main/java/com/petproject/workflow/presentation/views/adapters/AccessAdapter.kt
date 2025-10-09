package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemAccessInfoBinding
import com.petproject.workflow.domain.entities.Access
import com.petproject.workflow.domain.entities.AccessDurationType
import com.petproject.workflow.domain.entities.DocumentStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AccessAdapter(
    private val onItemClick: (Access) -> Unit = {}
) : ListAdapter<Access, AccessAdapter.AccessViewHolder>(AccessDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessViewHolder {
        return AccessViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: AccessViewHolder, position: Int) {
        val access = getItem(position)
        holder.bind(access)
        holder.itemView.setOnClickListener { onItemClick(access) }
    }

    class AccessViewHolder(
        private val binding: ItemAccessInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): AccessViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemAccessInfoBinding.inflate(inflater, parent, false)
                return AccessViewHolder(binding)
            }
        }

        private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

        fun bind(access: Access) {
            with(binding) {
                this.access = access

                // Настройка внешнего вида в зависимости от статуса
                setupStatusAppearance(access.status)

                // Форматирование дат
                tvCreatedAt.text = root.context.getString(
                    R.string.created_at_format,
                    access.createdAt.format(dateTimeFormatter)
                )

                tvUpdatedAt.text = root.context.getString(
                    R.string.updated_at_format,
                    access.updatedAt.format(dateTimeFormatter)
                )

                // Отображение срока действия
                setupValidUntil(access.validUntil)

                // Отображение типа доступа
                setupAccessType(access.type)

                tvAccessId.text = formatShortId(access.id)

                executePendingBindings()
            }
        }

        private fun setupStatusAppearance(status: DocumentStatus) {
            val context = binding.root.context
            with(binding) {
                when (status) {
                    DocumentStatus.VALID -> {
                        statusChip.setChipBackgroundColorResource(R.color.green)
                        statusChip.setTextColor(ContextCompat.getColor(context, R.color.white))
                        statusChip.text = context.getString(R.string.status_valid)
                        // Добавляем иконку для активного статуса (опционально)
                        statusChip.setChipIconResource(R.drawable.ic_check_circle)
                        statusChip.chipIconSize = 48f
                    }
                    DocumentStatus.EXPIRED -> {
                        statusChip.setChipBackgroundColorResource(R.color.red)
                        statusChip.setTextColor(ContextCompat.getColor(context, R.color.white))
                        statusChip.text = context.getString(R.string.status_expired)
                        // Добавляем иконку для истекшего статуса (опционально)
                        statusChip.setChipIconResource(R.drawable.ic_error)
                        statusChip.chipIconSize = 48f
                    }
                    DocumentStatus.EXPIRING -> {
                        statusChip.setChipBackgroundColorResource(R.color.orange)
                        statusChip.setTextColor(ContextCompat.getColor(context, R.color.white))
                        statusChip.text = context.getString(R.string.status_expiring)
                        // Добавляем иконку для истекающего статуса (опционально)
                        statusChip.setChipIconResource(R.drawable.ic_warning)
                        statusChip.chipIconSize = 48f
                    }
                }
            }
        }

        private fun setupValidUntil(validUntil: LocalDate?) {
            with(binding) {
                if (validUntil != null) {
                    tvValidUntil.text = root.context.getString(
                        R.string.valid_until_format,
                        validUntil.format(dateFormatter)
                    )
                    tvValidUntil.visibility = View.VISIBLE
                } else {
                    tvValidUntil.visibility = View.GONE
                }
            }
        }

        private fun setupAccessType(type: AccessDurationType) {
            with(binding) {
                tvAccessType.text = when (type) {
                    AccessDurationType.PERMANENT -> root.context.getString(R.string.access_type_permanent)
                    AccessDurationType.ONETIME -> root.context.getString(R.string.access_type_one_time)
                    else -> root.context.getString(R.string.access_type_temporary)
                }
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
    }
}