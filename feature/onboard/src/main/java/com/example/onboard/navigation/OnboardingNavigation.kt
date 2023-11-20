package com.example.onboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.onboard.LoginRoute
import com.nimble.ui.util.animatedComposable

const val ONBOARDING_GRAPH_ROUTE_PATTERN = "onboarding_graph"
const val loginRoute = "login_route"
fun NavGraphBuilder.onboardingGraph() {
  navigation(
    route = ONBOARDING_GRAPH_ROUTE_PATTERN,
    startDestination = loginRoute
  ) {
    animatedComposable(route = loginRoute) {
      LoginRoute()
    }
  }
}
