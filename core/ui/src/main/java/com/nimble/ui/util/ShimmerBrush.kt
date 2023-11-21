package com.nimble.ui.util

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.shimmerBrush(
  showShimmer: Boolean = true,
  targetValue: Float = 1000f,
  tweenDuration: Int = 700
): Modifier {
  return if (showShimmer) {
    composed {
      val shimmerColors = listOf(
        Color.DarkGray.copy(alpha = 0.6f),
        Color.DarkGray.copy(alpha = 0.2f),
        Color.DarkGray.copy(alpha = 0.6f),
      )

      val transition = rememberInfiniteTransition(label = "")
      val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
          animation = tween(
            durationMillis = tweenDuration,
            easing = LinearEasing,
          ),
          repeatMode = RepeatMode.Reverse
        ),
        label = ""
      )
      this.background(
        Brush.linearGradient(
          colors = shimmerColors,
          start = Offset.Zero,
          end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        ),
        shape = CircleShape
      )

    }
  } else {
    this
  }
}
