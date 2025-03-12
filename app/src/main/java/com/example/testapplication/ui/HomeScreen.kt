package com.example.testapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.testapplication.Favorites
import com.example.testapplication.Random
import com.example.testapplication.Search

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Search) }
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = null
            )
            Text(
                "Search"
            )
        }
        Button(
            onClick = { navController.navigate(Random) }
        ) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = null
            )
            Text(
                "Random"
            )
        }
        Button(
            onClick = { navController.navigate(Favorites) }
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null
            )
            Text(
                "Favorites"
            )
        }
    }
}
