package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.StorageDto
import com.petproject.workflow.data.network.models.WasteDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Storage
import com.petproject.workflow.domain.entities.Waste
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class StorageWasteMapper @Inject constructor() {

    fun mapDtoToEntity(dto: StorageDto): Storage {
        return Storage(
            id = dto.id,
            name = dto.name,
            totalVolume = dto.totalVolume,
            currentVolume = dto.currentVolume,
            currentWaste = dto.currentWaste?.let { mapDtoToEntity(it) } ,
            updatedAt = LocalDateTime.parse(dto.updatedAt),
            allowedWastes = dto.allowedWastes?.map { mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Storage): StorageDto {
        return StorageDto(
            id = entity.id,
            name = entity.name,
            totalVolume = entity.totalVolume,
            currentVolume = entity.currentVolume,
            currentWaste = entity.currentWaste?.let { mapEntityToDto(it) },
            updatedAt = entity.updatedAt.toString(),
            allowedWastes = entity.allowedWastes?.map { mapEntityToDto(it) }
        )
    }

    fun mapDtoToEntity(dto: WasteDto): Waste {
        return Waste(
            id = dto.id,
            name = dto.name,
            isRecycle = dto.isRecycle,
            allowedStorages = dto.allowedStorages?.map { mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Waste): WasteDto {
        return WasteDto(
            id = entity.id,
            name = entity.name,
            isRecycle = entity.isRecycle,
            allowedStorages = entity.allowedStorages?.map { mapEntityToDto(it) }
        )
    }
}