package com.example.jtkwibu.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): AnimeSearchResponse

    //Untuk deskripsi
    @GET("anime/{mal_id}/full")
    suspend fun getAnimeDetails(@Path("mal_id") malId: Int): AnimeDetailsResponse

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String, @Query("page") page: Int): AnimeSearchResponse
}
