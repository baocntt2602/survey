package com.example.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nimble.ui.util.shimmerBrush

@Composable
fun HomeShimmerView(
  modifier: Modifier = Modifier,
  shimmerHeight: Dp = 20.dp,
) {
  ConstraintLayout(
    modifier = modifier
      .background(Color.Black)
      .systemBarsPadding()
  ) {
    val (
      date,
      datePassed,
      avatar,
      footer
    ) = createRefs()

    Box(
      modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth(.3f)
        .height(shimmerHeight)
        .shimmerBrush()
        .constrainAs(date) {
          start.linkTo(parent.start, 20.dp)
          top.linkTo(parent.top, 10.dp)
        }
    )

    Box(
      modifier = Modifier
        .fillMaxWidth(.25f)
        .height(shimmerHeight)
        .shimmerBrush()
        .constrainAs(datePassed) {
          start.linkTo(date.start)
          top.linkTo(date.bottom, 5.dp)
        }
    )

    Box(
      modifier = Modifier
        .clip(CircleShape)
        .size(36.dp)
        .shimmerBrush()
        .constrainAs(avatar) {
          end.linkTo(parent.end, 20.dp)
          bottom.linkTo(datePassed.bottom)
        }
    )

    Column(
      modifier = Modifier
        .constrainAs(footer) {
          bottom.linkTo(parent.bottom)
          start.linkTo(date.start)
        }
        .padding(bottom = 20.dp)
    ) {

      Box(
        modifier = Modifier
          .height(shimmerHeight)
          .shimmerBrush()
          .fillMaxWidth(.1f)
      )

      Spacer(modifier = Modifier.height(16.dp))

      Box(
        modifier = Modifier
          .height(shimmerHeight)
          .shimmerBrush()
          .fillMaxWidth(.7f)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Box(
        modifier = Modifier
          .height(shimmerHeight)
          .shimmerBrush()
          .fillMaxWidth(.4f)
      )

      Spacer(modifier = Modifier.height(16.dp))

      Box(
        modifier = Modifier
          .height(shimmerHeight)
          .fillMaxWidth(.9f)
          .shimmerBrush()
      )

      Spacer(modifier = Modifier.height(8.dp))

      Box(
        modifier = Modifier
          .height(shimmerHeight)
          .fillMaxWidth(.5f)
          .shimmerBrush()
      )
    }
  }
}
