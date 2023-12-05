package com.example.onboard

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.onboarding.R
import com.nimble.ui.component.textfield.TextFieldState
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

  @get:Rule(order = 1)
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  private val emailState = TextFieldState({ it.isNotEmpty() })
  private val passwordState = TextFieldState({ it.length >= 3 })

  @Test
  fun loginScreen_default_state() {
    val defaultTextFieldState = TextFieldState()
    composeTestRule.setContent {
      LoginScreen(
        uiState = LoginUiState.Idle,
        emailTextFieldState = defaultTextFieldState,
        passwordTextFieldState = defaultTextFieldState,
        isLoginFormValid = defaultTextFieldState.isValid,
        onLogin = { }) {}
    }

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.email)
      )
      .assertExists()

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.password)
      )
      .assertExists()

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.log_in)
      )
      .assertExists()
  }

  @Test
  fun loginScreen_invalidLoginInput() {
    composeTestRule.setContent {
      LoginScreen(
        uiState = LoginUiState.Idle,
        emailTextFieldState = emailState,
        passwordTextFieldState = passwordState,
        isLoginFormValid = emailState.isValid && passwordState.isValid,
        onLogin = { }) {}
    }

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.email)
      )
      .performTextInput("aaa")

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.password)
      )
      .performTextInput("12")

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.log_in)
      )
      .assertExists()
      .assertIsNotEnabled()
  }

  @Test
  fun loginScreen_validLoginInput() {
    composeTestRule.setContent {
      LoginScreen(
        uiState = LoginUiState.Idle,
        emailTextFieldState = emailState,
        passwordTextFieldState = passwordState,
        isLoginFormValid = emailState.isValid && passwordState.isValid,
        onLogin = { }) {}
    }

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.email)
      )
      .performTextInput("bao")

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.password)
      )
      .performTextInput("123")

    composeTestRule
      .onNodeWithText(
        composeTestRule.activity.resources.getString(R.string.log_in)
      )
      .assertExists()
      .assertIsEnabled()
  }

  @Test
  fun loginScreen_loadingIndicator_whenApiIsPerforming() {
    composeTestRule.setContent {
      LoginScreen(
        uiState = LoginUiState.Loading,
        emailTextFieldState = emailState,
        passwordTextFieldState = passwordState,
        isLoginFormValid = emailState.isValid && passwordState.isValid,
        onLogin = { }) {}
    }

    composeTestRule
      .onNodeWithContentDescription(
        composeTestRule.activity.resources.getString(com.nimble.sample.ui.R.string.loading_description)
      )
      .assertExists()
  }

  @Test
  fun loginScreen_errorDialog_whenError() {
    val err = composeTestRule.activity.resources.getString(com.nimble.sample.ui.R.string.general_error)
    composeTestRule.setContent {
      LoginScreen(
        uiState = LoginUiState.LoginFailed(err),
        emailTextFieldState = emailState,
        passwordTextFieldState = passwordState,
        isLoginFormValid = emailState.isValid && passwordState.isValid,
        onLogin = { }) {}
    }

    composeTestRule
      .onNodeWithContentDescription(
        err
      )
      .assertExists()
  }
}
