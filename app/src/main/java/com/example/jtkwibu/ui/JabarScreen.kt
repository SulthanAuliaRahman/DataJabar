package com.example.jtkwibu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jtkwibu.viewmodel.JabarViewModel

@Composable
fun JabarScreen(jabarViewModel: JabarViewModel = viewModel()){
    val eduStat by jabarViewModel.eduStat.collectAsState()
    val isLoading by jabarViewModel.isLoading.collectAsState()
    var searchQuery by remember { mutableStateOf("")}

    LaunchedEffect(searchQuery) {
        jabarViewModel.fetchEduStat(searchQuery.ifEmpty { null })
    }

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ){
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Cari Kabupaten/Kota") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(eduStat) { item ->
                    Card (
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                    ) {
                        Column (
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Nama Kabupaten/Kota: ${item.namaKabupatenKota}",
                            )
                            Text(
                                text = "Lama Sekolah: ${item.harapanLamaSekolah}",
                            )
                        }
                    }
                }
            }
        }
    }
}