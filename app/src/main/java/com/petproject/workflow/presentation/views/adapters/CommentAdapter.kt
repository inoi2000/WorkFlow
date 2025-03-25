package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemCommentInfoBinding
import com.petproject.workflow.domain.entities.Comment

class CommentAdapter() :
    ListAdapter<Comment, CommentAdapter.CommentInfoViewHolder>(CommentDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentInfoBinding.inflate(inflater, parent, false)
        return CommentInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentInfoViewHolder, position: Int) {
        val comment = getItem(position)
        holder.binding.comment = comment
    }

    class CommentInfoViewHolder(
        val binding: ItemCommentInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}