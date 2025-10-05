package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Instruction

interface InstructionRepository {

    suspend fun getInstructionById(id: String): Instruction

    suspend fun getAllCurrentInstructions(): List<Instruction>
}