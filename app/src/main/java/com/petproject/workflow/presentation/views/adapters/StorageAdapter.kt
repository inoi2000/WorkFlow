package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.Storage

class StorageAdapter(
    private val onStorageClick: (Storage) -> Unit
) : ListAdapter<Storage, StorageInfoViewHolder>(StorageDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageInfoViewHolder =
        StorageInfoViewHolder.inflateFrom(parent, onStorageClick)

    override fun onBindViewHolder(holder: StorageInfoViewHolder, position: Int) {
        val storage = getItem(position)
        holder.bind(storage)
    }
}