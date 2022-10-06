package com.example.nasaimageandvideolibrary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(val state = searchViewModel.uiState.collectAsState().value) {
            SearchViewModel.SearchUiState.Empty -> Text("There is empty")
            is SearchViewModel.SearchUiState.Error -> Text(state.message)
            is SearchViewModel.SearchUiState.Loaded -> SearchScreenLoaded(state.data, navController)
            SearchViewModel.SearchUiState.Loading -> CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreenLoaded(uiModel: SearchUiModel, navController: NavController) {
    if(uiModel.response == null) {
        Text("Nothing found")
    }
    val items = uiModel.response!!.collection.items
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items.count()) { index ->
            val item = items[index].data.first()
            ListItem(
                text = {
                    Text(item.title)
                },
                secondaryText = {
                    Text(item.center)
                },
                modifier = Modifier.clickable {
                    val route = "${Routes.MEDIA}/${item.nasaId}"
                    navController.navigate(route)
                }
            )
        }
    }
}

@Composable
@Preview
fun SecondScreenPreview() {
    SearchScreen(rememberNavController())
}