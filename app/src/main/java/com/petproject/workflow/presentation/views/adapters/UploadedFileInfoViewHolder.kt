package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemUploadedFileBinding
import com.petproject.workflow.domain.entities.FileKey

class UploadedFileInfoViewHolder(
    val binding: ItemUploadedFileBinding,
    val onDeleteClick: (FileKey) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun inflateFrom(
            viewGroup: ViewGroup,
            onDeleteClick: (FileKey) -> Unit
        ): UploadedFileInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemUploadedFileBinding.inflate(inflater, viewGroup, false)
            return UploadedFileInfoViewHolder(binding, onDeleteClick)
        }
    }

    fun bind(fileKey: FileKey) {
        binding.apply {
            tvFileName.text = fileKey.key
            btnDelete.setOnClickListener {
                onDeleteClick(fileKey)
            }
        }
    }
}