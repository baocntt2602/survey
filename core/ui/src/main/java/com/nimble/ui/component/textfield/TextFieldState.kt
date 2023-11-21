package com.nimble.ui.component.textfield

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/** Base state holder class that contain edit text logic
 * Make it open so that we can later implement different logics
 * */
open class TextFieldState(
  private val validator: (String) -> Boolean = { true },
  private val errorFor: (String) -> Int? = { null }
) {

  var text: String by mutableStateOf("")
  var isFocused: Boolean by mutableStateOf(false)
    private set

  // was the TextField ever focused, used to display error if the text was at least once focused
  private var isFocusedDirty: Boolean = false
  private var displayErrors: Boolean by mutableStateOf(false)

  open val isValid: Boolean
    get() = validator(text)

  fun isFocusChanged(focused: Boolean) {
    isFocused = focused
    if (focused) isFocusedDirty = true
  }

  fun enableShowErrors() {
    // only show errors if the text was at least once focused
    if (isFocusedDirty) {
      displayErrors = true
    }
  }

  fun showError() = !isValid && displayErrors

  open fun getErrorMessage(): Int? {
    return if (showError()) {
      errorFor(text)
    } else {
      null
    }
  }
}
