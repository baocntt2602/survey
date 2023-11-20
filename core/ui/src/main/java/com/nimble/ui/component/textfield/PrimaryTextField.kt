package com.nimble.ui.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PrimaryTextField(
  modifier: Modifier = Modifier,
  state: TextFieldState = remember {
    TextFieldState()
  },
  label: String,
  hideLabelOnFocused: Boolean = state.hideLabelOnFocused,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  trailingIcon: @Composable (() -> Unit)? = null,
) {
  OutlinedTextField(
    value = state.text,
    onValueChange = {
      state.text = it
    },
    label = {
      if (hideLabelOnFocused) {
        Text(text = "")
      } else {
        Text(text = label)
      }
    },
    modifier = modifier
      .fillMaxWidth()
      .onFocusChanged { focusState ->
        state.isFocusChanged(focusState.isFocused)
        if (!focusState.isFocused) {
          state.enableShowErrors()
        }
      },
    trailingIcon = trailingIcon,
    isError = state.showError(),
    supportingText = {
      state.getErrorMessage()?.let {
        TextFieldError(it)
      }
    },
    visualTransformation = visualTransformation,
    shape = MaterialTheme.shapes.large,
    colors = OutlinedTextFieldDefaults.colors(
      focusedTextColor = Color.White,
      unfocusedTextColor = Color.White,
      focusedContainerColor = Color.Gray.copy(0.5f),
      unfocusedContainerColor = Color.Gray.copy(0.5f),
      errorContainerColor = Color.Gray.copy(0.5f),
      focusedBorderColor = Color.Transparent,
      unfocusedBorderColor = Color.Transparent
    )
  )
}
