package com.dian.prueba.network.service

import com.dian.prueba.data.nuFeed.model.NuFeedResponse

interface NuFeedAPIService {
    suspend fun getNuFeed(paginationIndex: Int): NuFeedResponse
}