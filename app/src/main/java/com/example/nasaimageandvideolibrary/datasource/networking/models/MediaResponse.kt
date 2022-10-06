package com.example.nasaimageandvideolibrary.datasource.networking.models
import com.google.gson.annotations.SerializedName


data class MediaResponse(
    @SerializedName("collection")
    val collection: MediaCollection
)

data class MediaCollection(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<MediaItem>,
    @SerializedName("version")
    val version: String
)

data class MediaItem(
    @SerializedName("href")
    val href: String
)