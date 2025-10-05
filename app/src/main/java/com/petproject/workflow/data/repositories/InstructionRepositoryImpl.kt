package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.InstructionApiService
import com.petproject.workflow.data.network.mappers.InstructionMapper
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Instruction
import com.petproject.workflow.domain.repositories.InstructionRepository
import java.io.IOException
import javax.inject.Inject

class InstructionRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val instructionMapper: InstructionMapper,
    private val instructionApiService: InstructionApiService
): InstructionRepository {

    override suspend fun getInstructionById(id: String): Instruction {
        val response = instructionApiService.getInstructionById(id)
        if (response.isSuccessful) {
            response.body()?.let { return instructionMapper.mapDtoToEntity(it) }
        }
        throw IOException()
    }

    override suspend fun getAllCurrentInstructions(): List<Instruction> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = instructionApiService.getAllInstructionsByEmployeeId(employeeId)
        return response.map { instructionMapper.mapDtoToEntity(it) }
    }

}