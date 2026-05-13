package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemProductInfoBinding
import com.petproject.workflow.domain.entities.Product

class ProductInfoViewHolder(
    val binding: ItemProductInfoBinding,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun inflateFrom(viewGroup: ViewGroup, onProductClick: (Product) -> Unit): ProductInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemProductInfoBinding.inflate(inflater, viewGroup, false)
            return ProductInfoViewHolder(binding, onProductClick)
        }
    }

    fun bind(product: Product) {
        binding.product = product

        binding.buttonDetails.setOnClickListener {
            onProductClick(product)
        }

    }
}