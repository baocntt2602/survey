package com.nimble.ui.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TextFieldError(
  @StringRes errorText: Int,
  modifier: Modifier = Modifier
) {
  Text(
    text = stringResource(id = errorText),
    modifier = modifier.fillMaxWidth(),
    color = MaterialTheme.colorScheme.error
  )
}
