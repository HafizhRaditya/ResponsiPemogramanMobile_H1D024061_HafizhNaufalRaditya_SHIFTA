package com.pemmob1.h1d024061.responsi.data.repository

import com.pemmob1.h1d024061.responsi.data.model.Digimon
import com.pemmob1.h1d024061.responsi.data.model.DigimonDetail
import com.pemmob1.h1d024061.responsi.data.remote.ApiClient
import com.pemmob1.h1d024061.responsi.data.remote.DigimonApi

class DigimonRepository(
    private val api: DigimonApi = ApiClient.digimonApi,
) {

    suspend fun getDigimonList(): List<Digimon> =
        api.getDigimonList(page = FIRST_PAGE, pageSize = PAGE_SIZE).content.orEmpty()

    suspend fun getDigimonDetail(id: Int): DigimonDetail =
        api.getDigimonDetail(id)

    private companion object {
        const val FIRST_PAGE = 0
        const val PAGE_SIZE = 2000
    }
}
