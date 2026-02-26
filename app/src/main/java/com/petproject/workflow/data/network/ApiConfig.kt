package com.petproject.workflow.data.network

object ApiConfig {

    const val BASE_EMPLOYEE_API = "https://e-workflow.ru:9300/"

    const val BASE_INSTRUCTION_API = "https://e-workflow.ru:9300/"
    const val BASE_ACCESS_API = "https://e-workflow.ru:9300/"

    const val BASE_CAR_API = "https://e-workflow.ru:9600/"
    const val BASE_TRAILER_API = "https://e-workflow.ru:9600/"
    const val BASE_FUELLING_API = "https://e-workflow.ru:9600/"
    const val BASE_JOURNEY_API = "https://e-workflow.ru:9600/"
    const val BASE_STATEMENT_API = "https://e-workflow.ru:9600/"

//    const val BASE_CAR_API = "http://192.168.0.159:9600/"
//    const val BASE_TRAILER_API = "http://192.168.0.159:9600/"
//    const val BASE_FUELLING_API = "http://192.168.0.159:9600/"
//    const val BASE_JOURNEY_API = "http://192.168.0.159:9600/"
//    const val BASE_STATEMENT_API = "http://192.168.0.159:9600/"

    const val BASE_ANNOUNCEMENT_API = "https://e-workflow.ru:9500/"

    const val BASE_TASK_API = "http://192.168.0.155:9200/"
    const val BASE_COMMENT_API = "http://192.168.0.155:9200/"
    const val BASE_ABSENCE_API = "http://192.168.0.155:9400/"

    const val EMPLOYEE_PHOTO_URI_PATTERN = BASE_EMPLOYEE_API + "api/employees/%s/photo"
    const val ANNOUNCEMENT_PHOTO_URI_PATTERN = BASE_ANNOUNCEMENT_API + "api/announcements/%s/poster"
}