package com.nimble.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.navigation.SURVEY_GRAPH_ROUTE_PATTERN
import com.example.onboard.navigation.ONBOARDING_GRAPH_ROUTE_PATTERN
import com.nimble.survey.ui.SurveyNavHost
import com.nimble.ui.theme.SurveyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    val splashScreen = installSplashScreen()
    var isLoadingData by mutableStateOf(true)

    // simulate loading data
    lifecycleScope.launch {
      delay(1000)
      isLoadingData = false
    }

    /**
     * Keep Splash Screen visible until we successfully load needed data
     */
    splashScreen.setKeepOnScreenCondition {
      isLoadingData
    }

    setContent {
      SurveyAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          SurveyNavHost(
            startDestination = if (viewModel.isAuthenticated) {
              SURVEY_GRAPH_ROUTE_PATTERN
            } else {
              ONBOARDING_GRAPH_ROUTE_PATTERN
            }
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  SurveyAppTheme {
    Greeting("Android")
  }
}
