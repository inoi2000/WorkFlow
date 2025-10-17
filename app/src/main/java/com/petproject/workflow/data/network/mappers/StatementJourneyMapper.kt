package com.petproject.workflow.data.network.mappers

import com.petproject.workflow.data.network.models.JourneyDto
import com.petproject.workflow.data.network.models.StatementDto
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.entities.JourneyStatus
import com.petproject.workflow.domain.entities.Statement
import java.time.LocalDateTime
import javax.inject.Inject

@ApplicationScope
class StatementJourneyMapper @Inject constructor(
    private val employeeMapper: EmployeeMapper,
    private val carMapper: CarMapper,
    private val trailerMapper: TrailerMapper
) {

    fun mapDtoToEntity(dto: StatementDto): Statement {
        return Statement(
            id = dto.id,
            logist = employeeMapper.mapDtoToEntity(dto.logist),
            data = dto.data,
            contactPhone = dto.contactPhone,
            destinationTime = LocalDateTime.parse(dto.destinationTime),
            destinationAddress = dto.destinationAddress,
            createdAt = LocalDateTime.parse(dto.createdAt),
            updatedAt = LocalDateTime.parse(dto.updatedAt),
            journey = dto.journey?.let { mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Statement): StatementDto {
        return StatementDto(
            id = entity.id,
            logist = employeeMapper.mapEntityToDto(entity.logist),
            data = entity.data,
            contactPhone = entity.contactPhone,
            destinationTime = entity.destinationTime.toString(),
            destinationAddress = entity.destinationAddress,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
            journey = entity.journey?.let { mapEntityToDto(it) }
        )
    }

    fun mapDtoToEntity(dto: JourneyDto): Journey {
        return Journey(
            id = dto.id,
            car = carMapper.mapDtoToEntity(dto.car),
            driver = employeeMapper.mapDtoToEntity(dto.driver),
            status = JourneyStatus.valueOf(dto.status),
            startOdometer = dto.startOdometer,
            endOdometer = dto.endOdometer,
            createdAt = LocalDateTime.parse(dto.createdAt),
            confirmedAt = dto.confirmedAt?.let { LocalDateTime.parse(it) },
            startedAt = dto.startedAt?.let { LocalDateTime.parse(it) },
            finishedAt = dto.finishedAt?.let { LocalDateTime.parse(it) },
            canceledAt = dto.canceledAt?.let { LocalDateTime.parse(it) },
            trailer = dto.trailer?.let { trailerMapper.mapDtoToEntity(it) },
            statement = dto.statement?.let { mapDtoToEntity(it) }
        )
    }

    fun mapEntityToDto(entity: Journey): JourneyDto {
        return JourneyDto(
            id = entity.id,
            car = carMapper.mapEntityToDto(entity.car),
            driver = employeeMapper.mapEntityToDto(entity.driver),
            status = entity.status.name,
            startOdometer = entity.startOdometer,
            endOdometer = entity.endOdometer,
            createdAt = entity.createdAt.toString(),
            confirmedAt = entity.confirmedAt?.toString(),
            startedAt = entity.startedAt?.toString(),
            finishedAt = entity.finishedAt?.toString(),
            canceledAt = entity.canceledAt?.toString(),
            trailer = entity.trailer?.let { trailerMapper.mapEntityToDto(it) },
            statement = entity.statement?.let { mapEntityToDto(it) }
        )
    }


}