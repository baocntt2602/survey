package com.example.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.nimble.ui.util.animatedComposable

//this can be moved to a feature module
private const val SURVEY_DETAIL_ROUTE = "detail_route"
internal const val surveyIdArg = "detail_arg"

fun NavController.navigateToSurveyDetail(surveyId: String) {
  this.navigate("$SURVEY_DETAIL_ROUTE/$surveyId")
}

fun NavGraphBuilder.detailScreen(
  onBackClick: () -> Unit,
) {
  animatedComposable(
    route = "$SURVEY_DETAIL_ROUTE/{$surveyIdArg}",
    arguments = listOf(
      navArgument(surveyIdArg) { type = NavType.StringType },
    ),
  ) {
    SurveyDetailRoute(
      onBackClick = onBackClick
    )
  }
}
