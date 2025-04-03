package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemTaskInfoBinding
import com.petproject.workflow.domain.entities.Task

class TaskInfoViewHolder(
    val binding: ItemTaskInfoBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object{
        const val EXECUTOR_MODE = 0
        const val INSPECTOR_MODE = 1

        fun inflateFrom(viewGroup: ViewGroup): TaskInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemTaskInfoBinding.inflate(inflater, viewGroup, false)
            return TaskInfoViewHolder(binding)
        }
    }

    fun bind(
        task: Task,
        mode: Int,
        onTaskClick: (String) -> Unit,
        onCountCommentsClick: (String) -> Unit
    ) {
        binding.task = task

        if (mode == EXECUTOR_MODE) {
            binding.nameTextView.text = task.inspector?.name
        } else
            if (mode == INSPECTOR_MODE) {
                binding.nameTextView.text = task.executor?.name
            }

        binding.itemRefButton.setOnClickListener {
            onTaskClick(task.id)
        }

        binding.commentCountTextView.setOnClickListener {
            onCountCommentsClick(task.id)
        }
    }
}