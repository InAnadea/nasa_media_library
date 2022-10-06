package com.example.nasaimageandvideolibrary.datasource

import com.example.nasaimageandvideolibrary.datasource.networking.NetworkingService
import com.example.nasaimageandvideolibrary.datasource.networking.models.MediaResponse
import com.example.nasaimageandvideolibrary.datasource.networking.models.SearchResponse
import javax.inject.Inject

class MediaRepository @Inject constructor(private val networkingService: NetworkingService) {
    suspend fun search(query: String? = null): SearchResponse =
        networkingService.search(query)

    suspend fun asset(nasaId: String): MediaResponse = networkingService.asset(nasaId)
}