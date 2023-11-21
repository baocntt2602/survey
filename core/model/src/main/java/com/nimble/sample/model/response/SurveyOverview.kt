package com.nimble.sample.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SurveyOverview(
  val attributes: SurveyAttributes,
  val id: String,
  val type: String
)

data class SurveyAttributes(
  @JsonProperty("is_active")
  val isActive: Boolean? = null,
  @JsonProperty("thank_email_above_threshold")
  val thankEmailAboveThreshold: String? = null,
  @JsonProperty("cover_image_url")
  val coverImageUrl: String,
  val description: String? = null,
  @JsonProperty("created_at")
  val createdAt: String? = null,
  @JsonProperty("inactive_at")
  val inactiveAt: String? = null,
  val title: String? = null,
  @JsonProperty("active_at")
  val activeAt: String? = null,
  @JsonProperty("thank_email_below_threshold")
  val thankEmailBelowThreshold: String? = null,
  @JsonProperty("survey_type")
  val surveyType: String? = null
)
