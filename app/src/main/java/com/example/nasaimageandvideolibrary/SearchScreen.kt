package com.example.nasaimageandvideolibrary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    var searchFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(topBar = {
        TopAppBar {
            TextField(
                value = searchFieldValue,
                onValueChange = { value ->
                    searchFieldValue = value
                    searchViewModel.search(searchFieldValue.text)
                },
                label = { Text("Search request") },
                modifier = Modifier.fillMaxSize()
            )
        }
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = searchViewModel.uiState.collectAsState().value) {
                SearchViewModel.SearchUiState.Empty -> Text("There is empty")
                is SearchViewModel.SearchUiState.Error -> Text(state.message)
                is SearchViewModel.SearchUiState.Loaded -> SearchScreenLoaded(
                    state.data, navController
                )
                SearchViewModel.SearchUiState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreenLoaded(uiModel: SearchUiModel, navController: NavController) {
    if (uiModel.response?.collection?.items?.isEmpty() == true) {
        Text("Nothing found")
    }
    val items = uiModel.response!!.collection.items
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items.count()) { index ->
            val item = items[index].data.first()
            ListItem(text = {
                Text(item.title)
            }, secondaryText = {
                Text(item.center)
            }, modifier = Modifier.clickable {
                val route = "${Routes.MEDIA}/${item.nasaId}"
                navController.navigate(route)
            })
        }
    }
}

@Composable
@Preview
fun SecondScreenPreview() {
    SearchScreen(rememberNavController())
}