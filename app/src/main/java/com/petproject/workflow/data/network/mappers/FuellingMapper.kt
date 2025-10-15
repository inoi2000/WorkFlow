package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.FuellingDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Fuelling
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class FuellingMapper @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val carMapper: CarMapper
) {

    fun mapDtoToEntity(dto: FuellingDto): Fuelling {
        return Fuelling(
            id = dto.id,
            driver = employeeMapper.mapDtoToEntity(dto.driver),
            operator = employeeMapper.mapDtoToEntity(dto.operator),
            car = carMapper.mapDtoToEntity(dto.car),
            volume = dto.volume,
            createdAt = LocalDateTime.parse(dto.createdAt)
        )
    }

    fun mapEntityToDto(entity: Fuelling): FuellingDto {
        return FuellingDto(
            id = entity.id,
            driver = employeeMapper.mapEntityToDto(entity.driver),
            operator = employeeMapper.mapEntityToDto(entity.operator),
            car = carMapper.mapEntityToDto(entity.car),
            volume = entity.volume,
            createdAt = entity.createdAt.toString()
        )
    }
}