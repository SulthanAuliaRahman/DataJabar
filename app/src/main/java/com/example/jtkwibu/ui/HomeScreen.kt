package com.example.jtkwibu.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.jtkwibu.data.AnimeEntity
import com.example.jtkwibu.R

import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.LinkAnnotation
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onAnimeClick: (Int) -> Unit,
    viewModel: com.example.jtkwibu.viewmodel.HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    // Collect the paging items from view Model.
    val animeList = viewModel.animeList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {

        itemsIndexed(items = List(animeList.itemCount) { it }) { index, _ ->

            animeList[index]?.let { anime ->
                NetflixAnimeItem(anime = anime, onClick = { onAnimeClick(anime.malId) })
            }
        }
    }
}

@Composable
fun NetflixAnimeItem(anime: AnimeEntity, onClick: () -> Unit) {
    Log.d("HomeScreen", "Menampilkan anime: ${anime.title}, Image URL: ${anime.imageUrl}")

    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f) // Ensures the card is square
            .clickable { showDialog = true
                    coroutineScope.launch {
                }},
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Display the anime image
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading),
                error = painterResource(R.drawable.ic_launcher_foreground)
            )
            // Overlay a gradient at the bottom for better text readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black)
                        )
                    )
            )
            // Display the anime title on the gradient
            Text(
                text = anime.title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(4.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
    // Dialog untuk menampilkan opsi bookmark
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Column {
                    Text("Bookmark Anime") // Title utama
                    Spacer(modifier = Modifier.height(4.dp))
                    anime.synopsis?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
                    },
            text = {
                Text(if (anime.isBookmarked) "Hapus anime dari bookmark?" else "Tambahkan anime ke bookmark?")
            },
            confirmButton = {
                Button(onClick = {
//                    onBookmarkClick()
                    showDialog = false
                }) {
                    Text(if (anime.isBookmarked) "Hapus" else "Tambahkan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
        }
}
