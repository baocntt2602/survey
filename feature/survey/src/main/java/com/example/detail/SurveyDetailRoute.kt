package com.example.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.extension.hqCoverUrl
import com.nimble.sample.model.response.SurveyAttributes

@Composable
fun SurveyDetailRoute(
  onBackClick: () -> Unit,
  viewModel: SurveyDetailViewModel = hiltViewModel()
) {

  val survey: SurveyAttributes? by viewModel.survey.collectAsStateWithLifecycle()
  SurveyScreen(survey) {
    onBackClick()
  }
}

@Composable
fun SurveyScreen(
  survey: SurveyAttributes?,
  modifier: Modifier = Modifier,
  onBackClick: () -> Unit
) {

  Box(
    modifier = modifier.fillMaxSize()
  ) {

    AsyncImage(
      model = survey?.hqCoverUrl,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize()
    )

    Column(
      modifier = Modifier.statusBarsPadding()
    ) {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          tint = MaterialTheme.colorScheme.primary,
          contentDescription = null
        )
      }

      Text(
        text = survey?.title.orEmpty(),
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.W700),
        textAlign = TextAlign.Center,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 20.dp)
      )
    }
  }
}
