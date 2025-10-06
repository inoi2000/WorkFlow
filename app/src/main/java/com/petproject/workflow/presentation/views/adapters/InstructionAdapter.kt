package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemInstructionInfoBinding
import com.petproject.workflow.domain.entities.Instruction

class InstructionAdapter : ListAdapter<Instruction, InstructionAdapter.InstructionInfoViewHolder>(
    InstructionDiffItemCallback
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionInfoViewHolder {
        return InstructionInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: InstructionInfoViewHolder, position: Int) {
        val instruction = getItem(position)
        holder.bind(instruction)
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

        fun bind(
            instruction: Instruction
        ) {
            binding.instruction = instruction
        }
    }
}