package com.example.onboard

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginRoute(
  modifier: Modifier = Modifier,
  viewModel: LoginViewModel = hiltViewModel()
) {
  LoginScreen(modifier)
}

@Composable
fun LoginScreen(
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
  ) {
    Text(text = "Login", color = Color.Black)
  }
}
