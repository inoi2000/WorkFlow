package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemAbsenceInfoBinding
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceStatus
import com.petproject.workflow.domain.entities.AbsenceType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class AbsenceInfoViewHolder(
    val binding: ItemAbsenceInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun inflateFrom(viewGroup: ViewGroup): AbsenceInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemAbsenceInfoBinding.inflate(inflater, viewGroup, false)
            return AbsenceInfoViewHolder(binding)
        }
    }

    fun bind(
        absence: Absence,
        mode: Int,
        onAbsenceClick: (String) -> Unit
    ) {
        binding.absence = absence

        // Устанавливаем все вычисляемые значения
        setAbsenceType(absence)
        setDaysCount(absence)
        setStatusChip(absence)
        setDateRange(absence)
        setCreatedAt(absence)
        setEmployeeRow(mode)

        binding.executePendingBindings()
    }

    private fun setEmployeeRow(mode: Int) {
        when (mode) {
            0 -> binding.employeeRow.visibility = View.GONE
            1 -> binding.employeeRow.visibility = View.VISIBLE
        }
    }

    private fun setAbsenceType(absence: Absence) {
        val absenceTypeText = when (absence.policy.type) {
            AbsenceType.VACATION -> "Ежегодный отпуск"
            AbsenceType.BUSINESS_TRIP -> "Командировка"
            AbsenceType.SICK_LEAVE -> "Больничный"
            AbsenceType.DAY_OFF -> "Отгул"
            else -> ""
        }
        binding.itemTitle.text = absenceTypeText
    }

    private fun setDaysCount(absence: Absence) {
        val days = if (LocalDate.now() < absence.startDate)
            ChronoUnit.DAYS.between(
                LocalDate.now(), absence.startDate
            ) else 0
        val daysInt = days.toInt()
        val daysText = when {
            daysInt % 10 == 1 && daysInt % 100 != 11 -> "день"
            daysInt % 10 in 2..4 && daysInt % 100 !in 12..14 -> "дня"
            else -> "дней"
        }
        binding.daysCountChip.text = "$daysInt $daysText"
        if (daysInt == 0) {
            binding.daysCountChip.setChipBackgroundColorResource(R.color.grey_dark)
        }
    }

    private fun setStatusChip(absence: Absence) {
        val (statusText, statusColorRes, statusIconRes) = when (absence.status) {
            AbsenceStatus.APPROVED -> Triple(
                "Согласовано",
                R.color.green,
                R.drawable.ic_check_circle
            )

            AbsenceStatus.NOT_APPROVED -> Triple(
                "Отклонено",
                R.color.red,
                R.drawable.ic_cancel
            )

            AbsenceStatus.ON_APPROVAL -> Triple(
                "На согласовании",
                R.color.orange,
                R.drawable.ic_pending
            )

            else -> Triple("", R.color.grey_dark, 0)
        }

        binding.absenceStatusChip.text = statusText
        binding.absenceStatusChip.setChipBackgroundColorResource(statusColorRes)

        if (statusIconRes != 0) {
            binding.absenceStatusChip.chipIcon =
                ContextCompat.getDrawable(itemView.context, statusIconRes)
            binding.absenceStatusChip.isChipIconVisible = true
        } else {
            binding.absenceStatusChip.isChipIconVisible = false
        }
    }

    private fun setDateRange(absence: Absence) {
        val monthFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale("ru"))
        val dateRangeText =
            "${absence.startDate.format(monthFormatter)} – ${absence.endDate.format(monthFormatter)}"
        binding.durationTextView.text = dateRangeText
    }

    private fun setCreatedAt(absence: Absence) {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("ru"))
        val createdAtText = "Создано ${absence.createdAt.format(formatter)}"
        binding.createdAtText.text = createdAtText
    }
}