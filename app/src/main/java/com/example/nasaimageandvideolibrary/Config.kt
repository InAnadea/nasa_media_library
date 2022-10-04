package com.example.nasaimageandvideolibrary

abstract class Config {
    companion object {
        @JvmStatic val NASA_API_KEY: String = BuildConfig.NASA_API_KEY
    }
}