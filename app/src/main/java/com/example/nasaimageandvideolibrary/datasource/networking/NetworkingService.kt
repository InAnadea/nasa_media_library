package com.example.nasaimageandvideolibrary.datasource.networking

import com.example.nasaimageandvideolibrary.datasource.networking.models.SearchResponse
import retrofit2.http.GET

interface NetworkingService {
    @GET("/search?q=apollo%2011")
    suspend fun search(): SearchResponse
}