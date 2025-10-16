package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemStatementInfoBinding
import com.petproject.workflow.domain.entities.Statement

class StatementAdapter :
    ListAdapter<Statement, StatementAdapter.StatementInfoViewHolder>(StatementDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementInfoViewHolder {
        return StatementInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: StatementInfoViewHolder, position: Int) {
        val statement = getItem(position)
        holder.bind(statement)
    }

    class StatementInfoViewHolder(
        val binding: ItemStatementInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(viewGroup: ViewGroup): StatementInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemStatementInfoBinding.inflate(inflater, viewGroup, false)
                return StatementInfoViewHolder(binding)
            }
        }

        fun bind(
            statement: Statement
        ) {
            binding.statement = statement
        }
    }
}