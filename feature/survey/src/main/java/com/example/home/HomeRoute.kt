package com.example.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.extension.toDateFormat
import com.example.extension.toPassedDays
import com.example.survey.R
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.ui.util.shimmerBrush

@Composable
fun HomeRoute(
  modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()
) {
  val uiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()
  HomeScreen(
    uiState,
    modifier = modifier
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
  uiState: HomeUiState,
  modifier: Modifier
) {
  Surface(
    modifier = modifier.fillMaxSize()
  ) {

    var showShimmer by remember {
      mutableStateOf(true)
    }

    var currentSurvey: SurveyOverview? by remember {
      mutableStateOf(null)
    }

    ConstraintLayout(
      modifier = Modifier
        .background(Color.Black)
    ) {
      val (
        date,
        datePassed,
        avatar,
        surveyTitle,
        surveyDescription,
        doSurveyBtn,
        pager,
        pagerIndicator,
      ) = createRefs()

      when (uiState) {
        is HomeUiState.SurveyLoaded -> {
          showShimmer = false
          val pagerState = rememberPagerState(pageCount = {
            uiState.surveys.size
          })
          currentSurvey = uiState.surveys[pagerState.currentPage]

          SurveyPagerView(
            pagerState = pagerState,
            surveys = uiState.surveys,
            modifier = modifier
              .constrainAs(pager) {}
              .fillMaxSize()
          )

          SurveyPagerIndicatorView(
            modifier = Modifier.constrainAs(pagerIndicator) {
              start.linkTo(surveyTitle.start)
              bottom.linkTo(surveyTitle.top, 5.dp)
            },
            pagerState = pagerState
          )
        }

        else -> {}
      }

      Text(
        text = currentSurvey?.attributes?.toDateFormat().orEmpty(),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
          .statusBarsPadding()
          .fillMaxWidth(0.5f)
          .shimmerBrush(showShimmer)
          .constrainAs(date) {
            start.linkTo(parent.start, 20.dp)
            top.linkTo(parent.top, 10.dp)
          }
      )

      Text(
        text = currentSurvey?.attributes?.toPassedDays().orEmpty(),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W700),
        modifier = Modifier
          .fillMaxWidth(0.5f)
          .shimmerBrush(showShimmer)
          .constrainAs(datePassed) {
            start.linkTo(date.start)
            top.linkTo(date.bottom, 5.dp)
          }
      )

      AsyncImage(
        model = "",
        contentDescription = null,
        modifier = Modifier
          .clip(CircleShape)
          .size(36.dp)
          .shimmerBrush(showShimmer)
          .constrainAs(avatar) {
            end.linkTo(parent.end, 20.dp)
            top.linkTo(date.top)
            bottom.linkTo(datePassed.bottom)
          }
      )

      Text(
        text = currentSurvey?.attributes?.title.orEmpty(),
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.W700),
        modifier = Modifier
          .shimmerBrush(showShimmer)
          .constrainAs(surveyTitle) {
            start.linkTo(surveyDescription.start)
            bottom.linkTo(surveyDescription.top, 5.dp)
            end.linkTo(doSurveyBtn.start, 20.dp)
            width = Dimension.fillToConstraints
          }
      )

      Text(
        text = currentSurvey?.attributes?.description.orEmpty(),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
          .navigationBarsPadding()
          .shimmerBrush(showShimmer)
          .constrainAs(surveyDescription) {
            bottom.linkTo(parent.bottom, 20.dp)
            start.linkTo(date.start)
            end.linkTo(doSurveyBtn.start, 20.dp)
            width = Dimension.fillToConstraints
          }
      )

      IconButton(onClick = {

      }, modifier = Modifier
        .systemBarsPadding()
        .constrainAs(doSurveyBtn) {
          end.linkTo(parent.end, 20.dp)
          bottom.linkTo(surveyDescription.bottom)
        }) {
        AsyncImage(
          model = R.drawable.ic_next,
          contentDescription = null,
          modifier = Modifier
            .background(Color.White, CircleShape)
            .size(50.dp)
            .padding(10.dp)
        )
      }
    }
  }
}
