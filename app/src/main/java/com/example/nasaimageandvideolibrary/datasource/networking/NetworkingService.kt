package com.example.nasaimageandvideolibrary.datasource.networking

import com.example.nasaimageandvideolibrary.datasource.networking.models.MediaResponse
import com.example.nasaimageandvideolibrary.datasource.networking.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkingService {
    @GET("/search")
    suspend fun search(@Query("q") query: String? = null): SearchResponse

    @GET("/asset/{nasa_id}")
    suspend fun asset(@Path("nasa_id") nasaId: String): MediaResponse
}