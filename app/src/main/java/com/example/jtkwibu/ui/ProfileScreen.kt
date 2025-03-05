package com.example.jtkwibu.ui

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jtkwibu.data.OnboardingPrefs
import com.example.jtkwibu.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ProfileScreen(context: Context, viewModel: ProfileViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()

    // Ambil data profile image dari ViewModel
    val profileImagePath by viewModel.profileImagePath.collectAsState(initial = null)

    // Gunakan state lokal agar gambar bisa di-refresh
    var refreshState by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.setProfileImage(it)
            refreshState = !refreshState // Toggle untuk memicu recomposition
        }
    }


    val userNameFlow = OnboardingPrefs.getUserName(context).collectAsState(initial = "")
    val userNimFlow = OnboardingPrefs.getUserNim(context).collectAsState(initial = "")

    var name by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }

    // Set nilai awal dari DataStore
    LaunchedEffect(userNameFlow.value, userNimFlow.value) {
        name = userNameFlow.value
        nim = userNimFlow.value
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (profileImagePath != null) {
            AsyncImage(
                model = File(profileImagePath!!),
                contentDescription = "Profile Image",
                modifier = Modifier.size(120.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Default Profile",
                modifier = Modifier.size(120.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Pilih Gambar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nim,
            onValueChange = { nim = it },
            label = { Text("NIM") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    OnboardingPrefs.saveUserData(context, name, nim)
                    refreshState = !refreshState // Toggle agar UI diperbarui
                }
            }
        ) {
            Text("Simpan Data")
        }
    }
}



