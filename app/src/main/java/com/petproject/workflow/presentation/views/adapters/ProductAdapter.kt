package com.petproject.workflow.presentation.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petproject.workflow.domain.entities.Product

class ProductAdapter(
    private val onProductClick: (Product) -> Unit
) : ListAdapter<Product, ProductInfoViewHolder>(ProductDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInfoViewHolder =
        ProductInfoViewHolder.inflateFrom(parent, onProductClick)

    override fun onBindViewHolder(holder: ProductInfoViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}