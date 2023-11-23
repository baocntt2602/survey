package com.example.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.example.survey.R
import com.nimble.sample.model.response.SurveyAttributes
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

val SurveyAttributes.hqCoverUrl: String
  get() = this.coverImageUrl + "l"

fun SurveyAttributes.toDateFormat(): String {
  val outputPattern = DateTimeFormatter.ofPattern("EEEE, MMMM dd")
  val createdLocalDate = this.createdAt?.toLocalDate() ?: return ""
  return createdLocalDate.format(outputPattern)
}

@Composable
fun SurveyAttributes.toPassedDays(): String {
  val createdLocalDate = this.createdAt?.toLocalDate() ?: return ""
  val now = LocalDate.now()
  val period = Period.between(createdLocalDate, now)

  val years = period.years
  val months = period.months
  val days = period.days

  return when {
    years > 0 -> pluralStringResource(id = R.plurals.year_ago, count = years, years)
    months > 0 -> pluralStringResource(id = R.plurals.months_ago, count = months, months)
    days > 0 -> pluralStringResource(id = R.plurals.days_ago, count = days, days)
    else -> stringResource(id = R.string.today)
  }
}

fun String.toLocalDate(): LocalDate {
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
  return LocalDate.parse(this, formatter)
}
