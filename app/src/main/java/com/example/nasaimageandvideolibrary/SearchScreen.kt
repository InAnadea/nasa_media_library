package com.example.nasaimageandvideolibrary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(val state = searchViewModel.uiState.collectAsState().value) {
            SearchViewModel.SearchUiState.Empty -> Text("There is empty")
            is SearchViewModel.SearchUiState.Error -> Text(state.message)
            is SearchViewModel.SearchUiState.Loaded -> Text(state.data.count.toString())
            SearchViewModel.SearchUiState.Loading -> CircularProgressIndicator()
        }
    }
}

@Composable
@Preview
fun SecondScreenPreview() {
    SearchScreen()
}