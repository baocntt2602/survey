package com.example.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.extension.hqCoverUrl
import com.nimble.sample.model.response.SurveyAttributes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyPagerView(
  modifier: Modifier = Modifier,
  pagerState: PagerState,
  surveys: LazyPagingItems<SurveyAttributes>
) {
  HorizontalPager(
    state = pagerState,
    modifier = modifier
  ) { page ->
    Box {
      AsyncImage(
        model = surveys[page]?.hqCoverUrl,
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
  indicatorCount: Int = 5,
  indicatorSize: Dp = 10.dp,
  indicatorShape: Shape = CircleShape,
  space: Dp = 8.dp,
  onClick: ((Int) -> Unit)? = null
) {

  val listState = rememberLazyListState()

  val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
  val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

  val currentItem by remember {
    derivedStateOf {
      pagerState.currentPage
    }
  }

  val itemCount = pagerState.pageCount

  LaunchedEffect(key1 = currentItem) {
    val viewportSize = listState.layoutInfo.viewportSize
    listState.animateScrollToItem(
      currentItem,
      (widthInPx / 2 - viewportSize.width / 2).toInt()
    )
  }


  LazyRow(
    modifier = modifier.width(totalWidth),
    state = listState,
    contentPadding = PaddingValues(vertical = space),
    horizontalArrangement = Arrangement.spacedBy(space),
    userScrollEnabled = false
  ) {

    items(itemCount) { index ->

      val isSelected = (index == currentItem)

      val centerItemIndex = indicatorCount / 2

      val right1 =
        (currentItem < centerItemIndex &&
          index >= indicatorCount - 1)

      val right2 =
        (currentItem >= centerItemIndex &&
          index >= currentItem + centerItemIndex &&
          index <= itemCount - centerItemIndex + 1)
      val isRightEdgeItem = right1 || right2

      val isLeftEdgeItem =
        index <= currentItem - centerItemIndex &&
          currentItem > centerItemIndex &&
          index < itemCount - indicatorCount + 1

      Box(
        modifier = Modifier
          .graphicsLayer {
            val scale = if (isSelected) {
              1f
            } else if (isLeftEdgeItem || isRightEdgeItem) {
              .5f
            } else {
              .8f
            }
            scaleX = scale
            scaleY = scale

          }
          .clip(indicatorShape)
          .size(indicatorSize)
          .background(
            if (isSelected) Color.LightGray else Color.DarkGray,
            indicatorShape
          )
          .then(
            if (onClick != null) {
              Modifier
                .clickable {
                  onClick.invoke(index)
                }
            } else Modifier
          )
      )
    }
  }
}
