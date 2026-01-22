package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.Task

class TaskAdapter(
    private val mode: Int,
    private val onTaskClick: (String) -> Unit,
    private val onCountCommentsClick: (String) -> Unit
) : ListAdapter<Task, TaskInfoViewHolder>(TaskDiffItemCallback) {

    companion object {
        const val EXECUTOR_MODE = 0
        const val INSPECTOR_MODE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskInfoViewHolder =
        TaskInfoViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskInfoViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(
            task,
            mode,
            onTaskClick,
            onCountCommentsClick
        )
    }
}