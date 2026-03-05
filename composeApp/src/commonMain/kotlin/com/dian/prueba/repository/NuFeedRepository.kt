package com.dian.prueba.repository

import com.dian.prueba.data.nuFeed.model.NuFeedResponse
import com.dian.prueba.network.service.NuFeedAPIService

interface NuFeedRepository {
    suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse
}

class NuFeedRepositoryImpl(
    private val nuFeedAPIService: NuFeedAPIService
) : NuFeedRepository {
    override suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse {
        return nuFeedAPIService.getNuFeed(paginationIndex)
    }
}