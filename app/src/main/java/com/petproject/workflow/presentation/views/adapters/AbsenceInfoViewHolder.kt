package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemAbsenceInfoBinding
import com.petproject.workflow.domain.entities.Absence

class AbsenceInfoViewHolder(
    val binding: ItemAbsenceInfoBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object{

        fun inflateFrom(viewGroup: ViewGroup): AbsenceInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemAbsenceInfoBinding.inflate(inflater, viewGroup, false)
            return AbsenceInfoViewHolder(binding)
        }
    }

    fun bind(
        absence: Absence
    ) {
        binding.absence = absence
    }
}