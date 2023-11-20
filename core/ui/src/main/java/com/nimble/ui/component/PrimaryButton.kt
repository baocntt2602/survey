package com.nimble.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
  modifier: Modifier = Modifier,
  text: String,
  isEnabled: Boolean = true,
  onClick: () -> Unit,
  height: Int = 48,
  shape: Shape = MaterialTheme.shapes.medium,
  enabledBackgroundColor: Color = MaterialTheme.colorScheme.background,
  enabledTextColor: Color = MaterialTheme.colorScheme.onBackground,
  disabledBackgroundColor: Color = MaterialTheme.colorScheme.surface,
  disabledTextColor: Color = MaterialTheme.colorScheme.onSurface,
) {
  Button(
    onClick = onClick,
    enabled = isEnabled,
    modifier = modifier
      .height(height.dp)
      .fillMaxWidth()
      .background(
        if (isEnabled) enabledBackgroundColor else disabledBackgroundColor,
        shape = shape
      ),
    colors = ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = if (isEnabled) enabledTextColor else disabledTextColor
    ),
    shape = shape
  ) {
    Text(
      text = text,
      color = if (isEnabled) enabledTextColor else disabledTextColor,
      style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700)
    )
  }
}
