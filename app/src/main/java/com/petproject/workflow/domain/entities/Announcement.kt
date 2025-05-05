package com.petproject.workflow.domain.entities

import java.time.LocalDate

data class Announcement(
    val id: String = "",
    val title: String,
    val postData: LocalDate,
    val content: String,
    val imgUrl: String?
)
