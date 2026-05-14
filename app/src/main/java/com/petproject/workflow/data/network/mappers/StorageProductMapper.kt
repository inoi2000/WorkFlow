package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.StorageDto
import com.petproject.workflow.data.network.models.ProductDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Storage
import com.petproject.workflow.domain.entities.Product
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class StorageProductMapper @Inject constructor() {

    fun mapDtoToEntity(dto: StorageDto): Storage {
        return Storage(
            id = dto.id,
            name = dto.name,
            totalVolume = dto.totalVolume,
            currentVolume = dto.currentVolume,
            currentProduct = dto.currentProduct?.let { mapDtoToEntity(it) } ,
            allowedMixing = dto.allowedMixing,
            outputProduct = dto.outputProduct?.let { mapDtoToEntity(it) } ,
            sector = dto.sector,
            placement = dto.placement,
            location = dto.location,
            updatedAt = LocalDateTime.parse(dto.updatedAt),
            allowedProducts = dto.allowedProducts?.map { mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Storage): StorageDto {
        return StorageDto(
            id = entity.id,
            name = entity.name,
            totalVolume = entity.totalVolume,
            currentVolume = entity.currentVolume,
            currentProduct = entity.currentProduct?.let { mapEntityToDto(it) },
            allowedMixing = entity.allowedMixing,
            outputProduct = entity.outputProduct?.let { mapEntityToDto(it) } ,
            sector = entity.sector,
            placement = entity.placement,
            location = entity.location,
            updatedAt = entity.updatedAt.toString(),
            allowedProducts = entity.allowedProducts?.map { mapEntityToDto(it) }
        )
    }

    fun mapDtoToEntity(dto: ProductDto): Product {
        return Product(
            id = dto.id,
            name = dto.name,
            density = dto.density,
            status = dto.status,
            type = dto.type,
            allowedStorages = dto.allowedStorages?.map { mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Product): ProductDto {
        return ProductDto(
            id = entity.id,
            name = entity.name,
            density = entity.density,
            status = entity.status,
            type = entity.type,
            allowedStorages = entity.allowedStorages?.map { mapEntityToDto(it) }
        )
    }
}