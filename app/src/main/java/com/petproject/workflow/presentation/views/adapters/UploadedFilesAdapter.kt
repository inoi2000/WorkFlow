package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemUploadedFileBinding
import com.petproject.workflow.domain.entities.FileKey

class UploadedFilesAdapter(
    private val onDeleteClick: (FileKey) -> Unit
) : ListAdapter<FileKey, UploadedFilesAdapter.ViewHolder>(FileKeyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUploadedFileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemUploadedFileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(fileKey: FileKey) {
            binding.apply {
                tvFileName.text = fileKey.key
                btnDelete.setOnClickListener {
                    onDeleteClick(fileKey)
                }
            }
        }
    }
}