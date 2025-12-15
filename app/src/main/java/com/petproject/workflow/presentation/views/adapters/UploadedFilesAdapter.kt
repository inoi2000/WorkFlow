package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.FileKey

class UploadedFilesAdapter(
    private val onDeleteClick: (FileKey) -> Unit
) : ListAdapter<FileKey, UploadedFileInfoViewHolder>(FileKeyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadedFileInfoViewHolder =
        UploadedFileInfoViewHolder.inflateFrom(parent, onDeleteClick)

    override fun onBindViewHolder(holder: UploadedFileInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}