package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemAbsenceInfoBinding
import com.petproject.workflow.domain.entities.Absence

class AbsenceAdapter() : ListAdapter<Absence, AbsenceAdapter.AbsenceInfoViewHolder>(AbsenceDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenceInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAbsenceInfoBinding.inflate(inflater, parent, false)
        return AbsenceInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbsenceInfoViewHolder, position: Int) {
        val absence = getItem(position)
        holder.binding.absence = absence
    }

    class AbsenceInfoViewHolder(
        val binding: ItemAbsenceInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}