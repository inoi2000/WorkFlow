package com.petproject.workflow.presentation.views.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemInstructionInfoBinding
import com.petproject.workflow.domain.entities.DocumentStatus
import com.petproject.workflow.domain.entities.Instruction
import com.petproject.workflow.domain.entities.InstructionConfirmation
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class InstructionAdapter : ListAdapter<Instruction, InstructionAdapter.InstructionInfoViewHolder>(
    InstructionDiffItemCallback
) {

    var onItemClickListener: ((Instruction) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionInfoViewHolder {
        return InstructionInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: InstructionInfoViewHolder, position: Int) {
        val instruction = getItem(position)
        holder.bind(instruction)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(instruction)
        }
    }

    class InstructionInfoViewHolder(
        val binding: ItemInstructionInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(viewGroup: ViewGroup): InstructionInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemInstructionInfoBinding.inflate(inflater, viewGroup, false)
                return InstructionInfoViewHolder(binding)
            }
        }

        fun bind(instruction: Instruction) {
            binding.instruction = instruction

            setupTitle(instruction.data.content)
            setupStatus(instruction.status)
            setupDates(instruction.updatedAt, instruction.validUntil)
            setupConfirmation(instruction.instructionConfirmation)

            binding.executePendingBindings()
        }

        private fun setupTitle(content: String) {
            binding.titleTextView.text = content
        }

        private fun setupStatus(status: DocumentStatus) {
            val context = binding.root.context

            when (status) {
                DocumentStatus.VALID -> {
                    binding.statusChip.setChipBackgroundColorResource(R.color.green_light)
                    binding.statusChip.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green_dark
                        )
                    )
                    binding.statusChip.text = context.getString(R.string.status_valid)
                    binding.statusLineView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                }

                DocumentStatus.EXPIRING -> {
                    binding.statusChip.setChipBackgroundColorResource(R.color.orange_light)
                    binding.statusChip.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.orange_dark
                        )
                    )
                    binding.statusChip.text = context.getString(R.string.status_expiring)
                    binding.statusLineView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.orange
                        )
                    )
                }

                DocumentStatus.EXPIRED -> {
                    binding.statusChip.setChipBackgroundColorResource(R.color.red_light)
                    binding.statusChip.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red_dark
                        )
                    )
                    binding.statusChip.text = context.getString(R.string.status_expired)
                    binding.statusLineView.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    )
                }
            }
        }

        private fun setupDates(updatedAt: LocalDateTime, validUntil: LocalDate?) {
            val context = binding.root.context

            // Дата обновления
            binding.updatedAtText.text = formatDateTime(context, updatedAt)

            // Срок действия
            if (validUntil != null) {
                binding.validUntilText.text = context.getString(
                    R.string.valid_until_format,
                    formatDate(context, validUntil)
                )
                binding.validUntilText.visibility = View.VISIBLE
            } else {
                binding.validUntilText.visibility = View.GONE
            }
        }

        private fun setupConfirmation(confirmation: InstructionConfirmation?) {
            val context = binding.root.context

            if (confirmation?.isConfirmed == true) {
                binding.confirmationStatus.setImageResource(R.drawable.ic_check_circle)
                binding.confirmationStatus.contentDescription =
                    context.getString(R.string.confirmed)
                binding.confirmationStatus.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.green)
                )

                val confirmedAtText = confirmation.confirmedAt?.let { date ->
                    context.getString(R.string.confirmed_at_format, formatDateTime(context, date))
                } ?: context.getString(R.string.confirmed)

                binding.confirmedAtText.text = confirmedAtText
                binding.confirmedAtText.visibility = View.VISIBLE
            } else {
                binding.confirmationStatus.setImageResource(R.drawable.ic_pending)
                binding.confirmationStatus.contentDescription =
                    context.getString(R.string.not_confirmed)
                binding.confirmationStatus.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.grey)
                )
                binding.confirmedAtText.visibility = View.GONE
            }
        }

        private fun formatDateTime(context: Context, localDateTime: LocalDateTime): String {
            return android.text.format.DateFormat.getMediumDateFormat(context).format(
                Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
            ) + " " + android.text.format.DateFormat.getTimeFormat(context).format(
                Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
            )
        }

        private fun formatDate(context: Context, localDate: LocalDate): String {
            return android.text.format.DateFormat.getMediumDateFormat(context).format(
                Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            )
        }
    }
}