package com.nimble.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nimble.sample.ui.R

@Composable
fun LoadingIndicator(
  description: String = stringResource(id = R.string.loading_description)
) {
  Dialog(
    onDismissRequest = {},
    properties = DialogProperties(
      dismissOnBackPress = false,
      dismissOnClickOutside = false
    )
  ) {
    CircularProgressIndicator(
      strokeCap = StrokeCap.Round,
      color = MaterialTheme.colorScheme.primary,
      modifier = Modifier
        .padding(16.dp)
        .semantics {
          contentDescription = description
        }
    )
  }
}
