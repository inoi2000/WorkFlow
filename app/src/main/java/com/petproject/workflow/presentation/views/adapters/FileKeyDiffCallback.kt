package com.petproject.workflow.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.petproject.workflow.domain.entities.FileKey

class FileKeyDiffCallback : DiffUtil.ItemCallback<FileKey>() {
    override fun areItemsTheSame(oldItem: FileKey, newItem: FileKey): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FileKey, newItem: FileKey): Boolean {
        return oldItem == newItem
    }
}