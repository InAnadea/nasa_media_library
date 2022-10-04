package com.example.nasaimageandvideolibrary

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun SecondScreen() {
    AsyncImage(
        model = "https://img.devrant.com/devrant/rant/r_1636728_WCFyi.jpg",
        contentDescription = null
    )
}

@Composable
@Preview
fun SecondScreenPreview() {
    SecondScreen()
}