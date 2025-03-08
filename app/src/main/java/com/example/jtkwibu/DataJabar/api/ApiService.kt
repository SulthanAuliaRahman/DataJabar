package com.example.jtkwibu.DataJabar.api

import com.example.jtkwibu.DataJabar.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("od_17052_harapan_lama_sekolah_menggunakan_sp2010__kabupaten")
    suspend fun getEduStat(
        @Query("search") search: String? = null
    ): Response<ApiResponse>
}