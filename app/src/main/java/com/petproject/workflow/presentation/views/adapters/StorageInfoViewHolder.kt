package com.petproject.workflow.presentation.views.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemStorageInfoBinding
import com.petproject.workflow.domain.entities.Storage
import java.time.format.DateTimeFormatter
import java.util.Locale

class StorageInfoViewHolder(
    private val binding: ItemStorageInfoBinding,
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

        binding.buttonDetails.setOnClickListener {
            onStorageClick(storage)
        }

        // Процент заполнения
        updateFillPercent(storage)

        // Информация о продукте
        updateProductInfo(storage)

        // Информация о локации
        updateLocationInfo(storage)

        // Детали объёма и веса
        updateVolumeAndWeightDetails(storage)

        // Дата обновления
        updateTimestamp(storage)

        // Обновление цветового индикатора
        updateFillIndicator(storage)

        // Выполняем привязки после всех изменений
        binding.executePendingBindings()
    }

    private fun updateFillPercent(storage: Storage) {
        val percent = calculateFillPercent(storage.currentVolume, storage.totalVolume)
        binding.textPercent.text = binding.root.context.getString(
            R.string.percent_format,
            percent
        )
    }

    private fun calculateFillPercent(current: Double, total: Double): Int {
        return if (total > 0) {
            ((current / total) * 100).toInt().coerceIn(0, 100)
        } else {
            0
        }
    }

    private fun updateProductInfo(storage: Storage) {
        val product = storage.currentProduct

        val ableTextColorId = ContextCompat.getColor(binding.root.context, R.color.grey_for_text)
        val unableTextColorId =
            ContextCompat.getColor(binding.root.context, android.R.color.darker_gray)

        if (product != null) {
            binding.textCurrentProduct.visibility = View.VISIBLE
            binding.iconProduct.setColorFilter(ableTextColorId)
            binding.iconLocation.setColorFilter(ableTextColorId)
            binding.textLocation.setTextColor(ableTextColorId)
            binding.textSector.setTextColor(ableTextColorId)
            binding.textLocation.setTypeface(null, Typeface.BOLD)
            binding.textSector.setTypeface(null, Typeface.BOLD)
            binding.textCurrentProduct.text = product.name
            binding.textNoProduct.visibility = View.GONE
        } else {
            binding.iconProduct.setColorFilter(unableTextColorId)
            binding.iconLocation.setColorFilter(unableTextColorId)
            binding.textLocation.setTextColor(unableTextColorId)
            binding.textSector.setTextColor(unableTextColorId)
            binding.textLocation.setTypeface(null, Typeface.NORMAL)
            binding.textSector.setTypeface(null, Typeface.NORMAL)
            binding.textCurrentProduct.visibility = View.GONE
            binding.textNoProduct.visibility = View.VISIBLE
        }
    }

    private fun updateLocationInfo(storage: Storage) {
        // Отображаем локацию и сектор
        binding.iconLocation.visibility = View.VISIBLE
        binding.textLocation.visibility = View.VISIBLE
        binding.textLocation.text = storage.location

        if (storage.sector.isNotBlank()) {
            binding.textSector.visibility = View.VISIBLE
            binding.textSector.text = storage.sector
        } else {
            binding.textSector.visibility = View.GONE
        }
    }

    private fun updateVolumeAndWeightDetails(storage: Storage) {
        // Форматирование объема
        binding.textVolumeDetails.text = String.format(
            Locale.getDefault(),
            "%.1f / %.1f м³",
            storage.currentVolume,
            storage.totalVolume
        )

        // Расчет и отображение веса на основе плотности продукта
        updateWeightDetails(storage)
    }

    private fun updateWeightDetails(storage: Storage) {
        val product = storage.currentProduct

        if (product != null && product.density > 0) {
            // Если есть продукт с известной плотностью, рассчитываем вес
            val currentWeight = storage.currentVolume * product.density
            val totalWeight = storage.totalVolume * product.density

            binding.textWeightDetails.text = String.format(
                Locale.getDefault(),
                "%.1f / %.1f т",
                currentWeight,
                totalWeight
            )
            binding.textWeightDetails.visibility = View.VISIBLE
        } else {
            // Если нет продукта или плотность неизвестна, скрываем информацию о весе
            binding.textWeightDetails.visibility = View.GONE
        }
    }

    private fun updateTimestamp(storage: Storage) {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        val formattedDate = storage.updatedAt.format(formatter)

        binding.textAdditionalInfo.text = binding.root.context.getString(
            R.string.updated_at_format,
            formattedDate
        )
        binding.textAdditionalInfo.visibility = View.VISIBLE
    }

    private fun updateFillIndicator(storage: Storage) {
        val percent = calculateFillPercent(storage.currentVolume, storage.totalVolume)

        when {
            percent >= 90 -> {
                binding.indicatorFillHigh.visibility = View.VISIBLE
                binding.indicatorFillMedium.visibility = View.VISIBLE
                binding.indicatorFillLow.visibility = View.VISIBLE
            }

            percent >= 60 -> {
                binding.indicatorFillHigh.visibility = View.INVISIBLE
                binding.indicatorFillMedium.visibility = View.VISIBLE
                binding.indicatorFillLow.visibility = View.VISIBLE
            }

            percent >= 30 -> {
                binding.indicatorFillHigh.visibility = View.INVISIBLE
                binding.indicatorFillMedium.visibility = View.INVISIBLE
                binding.indicatorFillLow.visibility = View.VISIBLE
            }

            else -> {
                binding.indicatorFillHigh.visibility = View.INVISIBLE
                binding.indicatorFillMedium.visibility = View.INVISIBLE
                binding.indicatorFillLow.visibility = View.INVISIBLE
            }
        }
    }

    fun onRecycled() {
        // Очистка ресурсов при переиспользовании view holder
        binding.textCurrentProduct.text = null
        binding.textVolumeDetails.text = null
        binding.textWeightDetails.text = null
        binding.textAdditionalInfo.text = null
    }
}