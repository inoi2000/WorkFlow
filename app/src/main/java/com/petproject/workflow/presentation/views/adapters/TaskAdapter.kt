package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemTaskInfoBinding
import com.petproject.workflow.domain.entities.Task

class TaskAdapter(
    private val mode: Int,
    private val onTaskClick: (String) -> Unit,
    private val onCountCommentsClick: (String) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskInfoViewHolder>(TaskDiffItemCallback) {

    companion object {
        const val EXECUTOR_MODE = 0
        const val INSPECTOR_MODE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskInfoBinding.inflate(inflater, parent, false)
        return TaskInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskInfoViewHolder, position: Int) {
        val task = getItem(position)
        holder.binding.task = task

        if (mode == EXECUTOR_MODE) {
            holder.binding.nameTextView.text = task.inspector?.name
        } else
        if (mode == INSPECTOR_MODE) {
            holder.binding.nameTextView.text = task.executor?.name
        }

        holder.binding.itemRefButton.setOnClickListener {
            onTaskClick(task.id)
        }

        holder.binding.commentCountTextView.setOnClickListener {
            onCountCommentsClick(task.id)
        }
    }

    class TaskInfoViewHolder(
        val binding: ItemTaskInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}