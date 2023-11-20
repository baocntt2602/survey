package com.example.home

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeRoute(
  modifier: Modifier = Modifier
) {
  HomeScreen(modifier = modifier)
}

@Composable
fun HomeScreen(
  modifier: Modifier
) {
  Surface(
    modifier = modifier
  ) {
    Text(text = "Home", color = Color.Black)
  }
}
