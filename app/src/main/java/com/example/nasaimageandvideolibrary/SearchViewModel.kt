package com.example.nasaimageandvideolibrary

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaimageandvideolibrary.datasource.MediaRepository
import com.example.nasaimageandvideolibrary.datasource.networking.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SearchViewModel @Inject constructor(
    private val repository: MediaRepository,
    @ApplicationContext private val applicationContext: Context,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val uiState: StateFlow<SearchUiState> = _uiState

    init {
        search("apollo 2011")
    }

    fun search(query: String? = null) {
        _uiState.value = SearchUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = repository.search(query)
                _uiState.value = SearchUiState.Loaded(
                    SearchUiModel(response)
                )
            } catch (ex: Exception) {
                if (ex is HttpException && ex.code() == 429) {
                    onQueryLimitReached()
                } else {
                    onErrorOccurred()
                }
            }
        }
    }

    private fun onQueryLimitReached() {
        _uiState.value = SearchUiState.Error(
            applicationContext.getString(R.string.query_limit_reached)
        )
    }

    private fun onErrorOccurred() {
        _uiState.value = SearchUiState.Error(
            applicationContext.getString(R.string.something_went_wrong)
        )
    }


    sealed class SearchUiState {
        object Empty : SearchUiState()
        object Loading : SearchUiState()
        class Loaded(val data: SearchUiModel) : SearchUiState()
        class Error(val message: String) : SearchUiState()
    }
}