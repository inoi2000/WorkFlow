package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.Absence

class AbsenceAdapter : ListAdapter<Absence, AbsenceInfoViewHolder>(AbsenceDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenceInfoViewHolder {
        return AbsenceInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: AbsenceInfoViewHolder, position: Int) {
        val absence = getItem(position)
        holder.bind(absence)
    }
}