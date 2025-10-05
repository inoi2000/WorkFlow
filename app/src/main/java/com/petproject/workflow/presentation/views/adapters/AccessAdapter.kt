package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemAccessInfoBinding
import com.petproject.workflow.domain.entities.Access

class AccessAdapter : ListAdapter<Access, AccessAdapter.AccessInfoViewHolder>(AccessDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessInfoViewHolder {
        return AccessInfoViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: AccessInfoViewHolder, position: Int) {
        val access = getItem(position)
        holder.bind(access)
    }

    class AccessInfoViewHolder(
        val binding: ItemAccessInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object{

            fun inflateFrom(viewGroup: ViewGroup): AccessInfoViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemAccessInfoBinding.inflate(inflater, viewGroup, false)
                return AccessInfoViewHolder(binding)
            }
        }

        fun bind(
            access: Access
        ) {
            binding.access = access
        }
    }
}