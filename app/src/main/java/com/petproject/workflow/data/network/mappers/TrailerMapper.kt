package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.TrailerDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Trailer
import javax.inject.Inject

@ApplicationScope
class TrailerMapper @Inject constructor() {

    fun mapDtoToEntity(dto: TrailerDto): Trailer {
        return Trailer(
            id = dto.id,
            brand = dto.brand,
            licensePlate = dto.licensePlate,
            volumeLiter = dto.volumeLiter,
            material = dto.material
        )
    }

    fun mapEntityToDto(entity: Trailer): TrailerDto {
        return TrailerDto(
            id = entity.id,
            brand = entity.brand,
            licensePlate = entity.licensePlate,
            volumeLiter = entity.volumeLiter,
            material = entity.material
        )
    }
}