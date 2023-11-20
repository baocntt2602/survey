package com.example.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.home.HomeRoute
import com.nimble.ui.util.animatedComposable

const val SURVEY_GRAPH_ROUTE_PATTERN = "survey_graph"
const val homeRoute = "home_route"

fun NavController.navigateToHome() {
  this.navigate(homeRoute) {
    popUpTo(graph.id) {
      inclusive = true
    }
  }
}

fun NavGraphBuilder.surveyNavigation() {
  navigation(
    route = SURVEY_GRAPH_ROUTE_PATTERN,
    startDestination = homeRoute
  ) {
    animatedComposable(route = homeRoute) {
      HomeRoute()
    }
  }
}
