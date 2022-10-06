package com.example.nasaimageandvideolibrary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun MediaScreen(id: String, navController: NavController, mediaViewModel: MediaViewModel = hiltViewModel()) {
    DisposableEffect(key1 = mediaViewModel) {
        mediaViewModel.getAsset(id)
        onDispose {}
    }

    when (val state = mediaViewModel.uiState.collectAsState().value) {
        MediaViewModel.MediaUiState.Empty -> Text("There is no data")
        is MediaViewModel.MediaUiState.Error -> Text(state.message)
        is MediaViewModel.MediaUiState.Loaded -> MediaLoadedState(state.data, navController)
        MediaViewModel.MediaUiState.Loading -> CircularProgressIndicator()
    }
}

@Composable
fun MediaLoadedState(data: MediaUiModel, navController: NavController) {
    val imageUrl = data.response?.collection?.items?.first()?.href!!

    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = null,
    ) {
        when(painter.state) {
            AsyncImagePainter.State.Empty -> SubcomposeAsyncImageContent()
            is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            is AsyncImagePainter.State.Error -> Text(painter.state.toString())
        }
    }
}