package com.example.jtkwibu.data

import com.google.gson.annotations.SerializedName

data class AnimeSearchResponse(
    @SerializedName("data") val data: List<AnimeNetworkModel>
)

data class AnimeDetailsResponse(
    @SerializedName("data") val data: AnimeData
)

data class AnimeData(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("synopsis") val synopsis: String?
)


data class AnimeNetworkModel(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: AnimeImages,
    @SerializedName("synopsis") val synopsis: String?
)

data class AnimeImages(
    @SerializedName("jpg") val jpg: AnimeJpg
)

data class AnimeJpg(
    @SerializedName("image_url") val imageUrl: String?
)
