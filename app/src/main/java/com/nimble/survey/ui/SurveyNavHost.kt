package com.nimble.survey.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.detail.detailScreen
import com.example.detail.navigateToSurveyDetail
import com.example.navigation.navigateToHome
import com.example.navigation.surveyGraph
import com.example.onboard.navigation.onboardingGraph

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SurveyNavHost(
  modifier: Modifier = Modifier,
  startDestination: String
) {

  val navController: NavHostController = rememberNavController()

  Scaffold(
    modifier = modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
  ) { innerPadding ->
    NavHost(
      modifier = Modifier
        .padding(innerPadding)
        .consumeWindowInsets(innerPadding)
        .windowInsetsPadding(
          WindowInsets.safeDrawing.only(
            WindowInsetsSides.Horizontal
          )
        ),
      navController = navController,
      startDestination = startDestination
    ) {

      onboardingGraph(
        onLoginSuccessfully = navController::navigateToHome
      )

      surveyGraph(
        navController::navigateToSurveyDetail
      ) {
        detailScreen {
          navController.popBackStack()
        }
      }
    }
  }
}
