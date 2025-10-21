package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.Absence

class AbsenceAdapter(
    private val mode: Int,
    private val onAbsenceClick: (String) -> Unit
) : ListAdapter<Absence, AbsenceInfoViewHolder>(AbsenceDiffItemCallback) {

    companion object {
        const val EMPLOYEE_MODE = 0
        const val HR_MODE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenceInfoViewHolder {
        return AbsenceInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: AbsenceInfoViewHolder, position: Int) {
        val absence = getItem(position)
        holder.bind(absence, mode, onAbsenceClick)
    }
}