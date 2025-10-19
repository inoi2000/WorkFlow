package com.petproject.workflow.domain.entities

import java.time.LocalDateTime

data class Announcement(
    val id: String,
    val title: String,
    val createdAt: LocalDateTime,
    val content: String,
    val imgUrl: String?
)
