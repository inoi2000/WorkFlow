package com.petproject.workflow.data.network.utils

import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateTimeHelper @Inject constructor() {
    val dateTimeFormatPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatPattern)
}