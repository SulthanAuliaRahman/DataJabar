package com.example.jtkwibu.DataJabar.repository

import android.util.Log
import com.example.jtkwibu.DataJabar.api.ApiService
import com.example.jtkwibu.DataJabar.model.ApiResponse

class EduStatRepository(private val apiService: ApiService) {

    suspend fun getEduStat(searchQuery: String? = null): ApiResponse? {
        return try {
            val response = apiService.getEduStat(searchQuery)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("EduStatRepository", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("EduStatRepository", "Exception: ${e.message}")
            null
        }
    }

}