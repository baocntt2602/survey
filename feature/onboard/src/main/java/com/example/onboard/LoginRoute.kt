package com.example.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nimble.sample.ui.R
import com.nimble.ui.component.ErrorDialog
import com.nimble.ui.component.LoadingIndicator
import com.nimble.ui.component.PrimaryButton
import com.nimble.ui.component.textfield.PrimaryTextField
import com.nimble.ui.component.textfield.TextFieldState

@Composable
fun LoginRoute(
  modifier: Modifier = Modifier,
  onLoginSuccessfully: () -> Unit,
  viewModel: LoginViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LoginScreen(
    modifier,
    uiState,
    viewModel.emailTextFieldState,
    viewModel.passwordTextFieldState,
    viewModel.loginFormValid,
    viewModel::onLoginClicked,
    onLoginSuccessfully
  )
}

@Composable
fun LoginScreen(
  modifier: Modifier = Modifier,
  uiState: LoginUiState,
  emailTextFieldState: TextFieldState,
  passwordTextFieldState: TextFieldState,
  isLoginFormValid: Boolean,
  onLogin: () -> Unit,
  onLoginSuccessfully: () -> Unit
) {
  Surface(
    modifier = modifier
  ) {

    when (uiState) {
      LoginUiState.Loading -> LoadingIndicator()
      is LoginUiState.LoginFailed -> ErrorDialog(message = uiState.errorMessage)
      LoginUiState.LoginSuccess -> {
        onLoginSuccessfully.invoke()
      }

      else -> {}
    }


    Box {
      Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.img_bg),
        contentScale = ContentScale.Crop,
        contentDescription = null
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
            alpha = 0.9f
          )
      )

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .padding(horizontal = 20.dp)
          .systemBarsPadding()
      ) {

        val focusRequester = remember { FocusRequester() }

        Image(
          modifier = Modifier.padding(vertical = 70.dp),
          painter = painterResource(id = R.drawable.ic_logo),
          contentScale = ContentScale.Fit,
          contentDescription = null
        )

        PrimaryTextField(
          label = stringResource(com.example.onboarding.R.string.email),
          state = emailTextFieldState,
          imeAction = ImeAction.Next,
          onImeAction = { focusRequester.requestFocus() },
          modifier = Modifier
            .padding(bottom = 10.dp)
            .focusRequester(focusRequester)
        )

        PrimaryTextField(
          label = stringResource(com.example.onboarding.R.string.password),
          state = passwordTextFieldState,
          visualTransformation = PasswordVisualTransformation(),
          trailingIcon = {
            TextButton(
              onClick = {

              }
            ) {
              Text(
                text = stringResource(com.example.onboarding.R.string.forgot),
                color = Color.White.copy(alpha = 0.5f)
              )
            }
          },
          imeAction = ImeAction.Done,
          onImeAction = {
            if (isLoginFormValid) {
              onLogin.invoke()
            }
          },
          modifier = Modifier
            .padding(bottom = 10.dp)
            .focusRequester(focusRequester)
        )

        PrimaryButton(
          text = stringResource(com.example.onboarding.R.string.log_in),
          onClick = onLogin,
          isEnabled = isLoginFormValid
        )
      }
    }
  }
}
