package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.databinding.ItemStorageInfoBinding
import com.petproject.workflow.domain.entities.Storage
import java.time.format.DateTimeFormatter

class StorageInfoViewHolder(
    val binding: ItemStorageInfoBinding,
    private val onStorageClick: (Storage) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun inflateFrom(
            viewGroup: ViewGroup,
            onStorageClick: (Storage) -> Unit
        ): StorageInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemStorageInfoBinding.inflate(inflater, viewGroup, false)
            return StorageInfoViewHolder(binding, onStorageClick)
        }
    }

    fun bind(storage: Storage) {
        binding.storage = storage

        // Кнопка «Подробнее»
        binding.buttonDetails.setOnClickListener {
            onStorageClick(storage)
        }

        // Процент заполнения (вычисление и формат)
        val percent = if (storage.totalVolume > 0) {
            (storage.currentVolume / storage.totalVolume * 100).toInt()
        } else {
            0
        }
        binding.textPercent.text = "$percent %"

        // Блок текущего отхода: видимость и текст
        val hasProduct = storage.currentProduct != null
        if (hasProduct) {
            binding.iconProduct.visibility = View.VISIBLE
            binding.textCurrentProduct.visibility = View.VISIBLE
            binding.textCurrentProduct.text = storage.currentProduct!!.name
            binding.textNoProduct.visibility = View.GONE
        } else {
            binding.iconProduct.visibility = View.GONE
            binding.textCurrentProduct.visibility = View.GONE
            binding.textNoProduct.visibility = View.VISIBLE
        }

        // Детали объёма (форматирование числа)
        binding.textVolumeDetails.text = "%.1f / %.1f м³".format(
            storage.currentVolume, storage.totalVolume
        )

        // Дата обновления (форматирование)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        binding.textAdditionalInfo.text = "Изменено: ${storage.updatedAt.format(formatter)}"
    }
}