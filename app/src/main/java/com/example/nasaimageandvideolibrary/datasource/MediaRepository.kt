package com.example.nasaimageandvideolibrary.datasource

import com.example.nasaimageandvideolibrary.datasource.networking.NetworkingService
import com.example.nasaimageandvideolibrary.datasource.networking.models.SearchResponse
import javax.inject.Inject

class MediaRepository @Inject constructor(private val networkingService: NetworkingService) {
    suspend fun search(): SearchResponse =
        networkingService.search()
}