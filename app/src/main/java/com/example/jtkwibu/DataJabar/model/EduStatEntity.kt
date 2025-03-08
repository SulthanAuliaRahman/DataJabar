package com.example.jtkwibu.DataJabar.model

import com.google.gson.annotations.SerializedName

data class EduStatEntity (
    val id: Int,
    @SerializedName("harapan_lama_sekolah")
    val harapanLamaSekolah: Float,
    @SerializedName("kode_kabupaten_kota")
    val kodeKabupatenKota: Int,
    @SerializedName("kode_provinsi")
    val kodeProvinsi: Int,
    @SerializedName("nama_kabupaten_kota")
    val namaKabupatenKota: String,
    @SerializedName("nama_provinsi")
    val namaProvinsi: String,
    val satuan: String,
    val tahun: Int
)