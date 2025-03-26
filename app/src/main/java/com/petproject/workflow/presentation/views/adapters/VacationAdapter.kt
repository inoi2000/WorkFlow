package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemVacationInfoBinding
import com.petproject.workflow.domain.entities.Vacation

class VacationAdapter() : ListAdapter<Vacation, VacationAdapter.VacationInfoViewHolder>(VacationDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacationInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVacationInfoBinding.inflate(inflater, parent, false)
        return VacationInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacationInfoViewHolder, position: Int) {
        val vacation = getItem(position)
        holder.binding.vacation = vacation
    }

    class VacationInfoViewHolder(
        val binding: ItemVacationInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}