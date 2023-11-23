package com.example.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.extension.hqCoverUrl
import com.nimble.sample.model.response.SurveyAttributes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyPagerView(
  modifier: Modifier = Modifier,
  pagerState: PagerState,
  surveys: List<SurveyAttributes>
) {
  HorizontalPager(
    state = pagerState,
    modifier = modifier
  ) { page ->
    Box {
      AsyncImage(
        model = surveys[page].hqCoverUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
      )

      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(
                Color.DarkGray,
                Color.Black,
              )
            ),
            alpha = 0.5f
          )
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyPagerIndicatorView(
  modifier: Modifier = Modifier,
  pagerState: PagerState,
) {
  Row(
    modifier = modifier
  ) {
    repeat(pagerState.pageCount) { iteration ->
      val color = if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
      Box(
        modifier = Modifier
          .padding(2.dp)
          .clip(CircleShape)
          .background(color)
          .size(9.dp)
      )
    }
  }
}
