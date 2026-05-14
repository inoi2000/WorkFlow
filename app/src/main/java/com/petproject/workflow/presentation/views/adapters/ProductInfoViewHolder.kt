package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemProductInfoBinding
import com.petproject.workflow.domain.entities.Product
import com.petproject.workflow.domain.entities.ProductType

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

        binding.textProductName.text = product.name
        binding.textProductType.text = when (product.type) {
            ProductType.PURCHASED_FOR_PRODUCTION ->
                binding.root.resources.getString(R.string.product_type_purchased)

            ProductType.OWN_PRODUCTION ->
                binding.root.resources.getString(R.string.product_type_own_production)

            ProductType.CUSTOMER_WASTE ->
                binding.root.resources.getString(R.string.product_type_customer_waste)

            ProductType.OWN_WASTE ->
                binding.root.resources.getString(R.string.product_type_own_waste)

            ProductType.CUSTOMER_OWNED ->
                binding.root.resources.getString(R.string.product_type_customer_owned)
        }
    }
}