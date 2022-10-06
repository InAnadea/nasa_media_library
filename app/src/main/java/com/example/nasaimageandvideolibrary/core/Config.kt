package com.example.nasaimageandvideolibrary.core

import com.example.nasaimageandvideolibrary.BuildConfig

abstract class Config {
    companion object {
        @JvmStatic val NASA_API_KEY: String = BuildConfig.NASA_API_KEY
    }
}