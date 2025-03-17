package com.petproject.workflow.presentation.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemTaskInfoBinding
import com.petproject.workflow.domain.entities.Task

class TaskAdapter(
    private val context: Context,
    private val onTaskClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskInfoViewHolder>(TaskDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskInfoBinding.inflate(inflater, parent, false)
        return TaskInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskInfoViewHolder, position: Int) {
        val task = getItem(position)
//        with(holder.binding) {
//            with(task) {
//                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
//                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
//                tvSymbols.text = String.format(symbolsTemplate, name, comparisonCurrency)
//                tvPrice.text = price
//                tvLastUpdate.text = String.format(lastUpdateTemplate, lastUpdate)
//                Picasso.get().load(imageUrl).into(holder.binding.ivLogoCoin)
//                root.setOnClickListener {
//                    onCoinClick(this)
//                }
//            }
//        }
    }

    class TaskInfoViewHolder(
        val binding: ItemTaskInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}