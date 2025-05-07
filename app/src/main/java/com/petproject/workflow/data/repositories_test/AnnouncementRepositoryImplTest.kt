package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.domain.entities.Announcement
import com.petproject.workflow.domain.repositories.AnnouncementRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class AnnouncementRepositoryImplTest @Inject constructor() : AnnouncementRepository {

    private val announcementList: MutableList<Announcement>

    init {
        val announcement1 = Announcement(
            id = UUID.randomUUID().toString(),
            title = "Проводится планновая вакцинация от COVID-19",
            postData = LocalDate.now(),
            content = "Просьба всем сотрудникам 25.12.2025 прибыть в пунк вакцинациии и получить привиыку от вирусв COVID-19. Данное мероприятие является обязательным к исполнению. При неявки необходимо предоставить объяснительную",
            imgUrl = null
        )
        announcementList = mutableListOf(announcement1)
    }

    override suspend fun getAllAnnouncement(): List<Announcement> {
        return announcementList
    }

    override suspend fun getAnnouncement(id: String): Announcement {
        TODO("Not yet implemented")
    }
}