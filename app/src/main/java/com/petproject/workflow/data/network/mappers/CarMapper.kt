package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.CarDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.entities.CarStatus
import javax.inject.Inject

@ApplicationScope
class CarMapper @Inject constructor() {

    fun mapDtoToEntity(dto: CarDto): Car {
        return Car(
            id = dto.id,
            brand =  dto.brand,
            model = dto.model,
            licensePlate = dto.licensePlate,
            vin = dto.vin,
            year = dto.year,
            color = dto.color,
            odometer = dto.odometer,
            status = CarStatus.valueOf(dto.status)
        )
    }

    fun mapEntityToDto(entity: Car): CarDto {
        return CarDto(
            id = entity.id,
            brand =  entity.brand,
            model = entity.model,
            licensePlate = entity.licensePlate,
            vin = entity.vin,
            year = entity.year,
            color = entity.color,
            odometer = entity.odometer,
            status = entity.status.name
        )
    }
}