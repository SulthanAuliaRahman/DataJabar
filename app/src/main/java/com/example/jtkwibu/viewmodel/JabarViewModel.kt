package com.example.jtkwibu.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jtkwibu.DataJabar.api.ApiClient
import com.example.jtkwibu.DataJabar.model.EduStatEntity
import com.example.jtkwibu.DataJabar.repository.EduStatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JabarViewModel : ViewModel() {
    private val _eduStat = MutableStateFlow<List<EduStatEntity>>(emptyList())
    val eduStat = _eduStat.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun fetchEduStat(searchQuery: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = EduStatRepository(ApiClient.apiService).getEduStat(searchQuery)
                Log.d("JabarViewModel", "Response: $response")

                if (response != null) {
                    _eduStat.value = response.data
                } else {
                    _errorMessage.value = "Gagal mendapatkan data"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Terjadi kesalahan"
                _eduStat.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}