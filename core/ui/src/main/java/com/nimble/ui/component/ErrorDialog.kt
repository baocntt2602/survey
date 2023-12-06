package com.nimble.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.nimble.sample.ui.R

@Composable
fun ErrorDialog(
  message: String?
) {
    var dialogVisible by remember { mutableStateOf(true) }

    LaunchedEffect(message) {
      dialogVisible = true
    }
    val configuration = LocalConfiguration.current

    if (dialogVisible) {
      AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
          .widthIn(max = configuration.screenWidthDp.dp - 80.dp)
          .semantics {
            contentDescription = message.orEmpty()
          }
        ,
        onDismissRequest = {},
        text = {
          Text(
            text = message ?: stringResource(id = R.string.general_error),
            style = MaterialTheme.typography.labelLarge
          )
        },
        confirmButton = {
          Text(
            text = stringResource(id = R.string.ok),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
              .padding(horizontal = 8.dp)
              .clickable { dialogVisible = false },
          )
        },
      )
    }
}
