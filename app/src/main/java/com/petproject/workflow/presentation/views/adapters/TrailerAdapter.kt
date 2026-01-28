package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.Trailer

class TrailerAdapter(
    private val onTrailerClick: (Trailer) -> Unit
) : ListAdapter<Trailer, TrailerInfoViewHolder>(TrailerDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerInfoViewHolder =
        TrailerInfoViewHolder.inflateFrom(parent, onTrailerClick)

    override fun onBindViewHolder(holder: TrailerInfoViewHolder, position: Int) {
        val trailer = getItem(position)
        holder.bind(trailer)
    }
}