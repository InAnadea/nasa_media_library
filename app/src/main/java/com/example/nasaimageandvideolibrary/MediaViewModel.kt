package com.example.nasaimageandvideolibrary

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
class MediaViewModel @Inject constructor(
    private val repository: MediaRepository,
    @ApplicationContext private val applicationContext: Context,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<MediaUiState>(MediaUiState.Empty)
    val uiState: StateFlow<MediaUiState> = _uiState

    public fun getAsset(nasaId: String) {
        _uiState.value = MediaUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = repository.asset(nasaId)
                _uiState.value = MediaUiState.Loaded(
                    MediaUiModel(response)
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
        _uiState.value = MediaUiState.Error(
            applicationContext.getString(R.string.query_limit_reached)
        )
    }

    private fun onErrorOccurred() {
        _uiState.value = MediaUiState.Error(
            applicationContext.getString(R.string.something_went_wrong)
        )
    }


    sealed class MediaUiState {
        object Empty : MediaUiState()
        object Loading : MediaUiState()
        class Loaded(val data: MediaUiModel) : MediaUiState()
        class Error(val message: String) : MediaUiState()
    }
}