package com.nimble.sample.mapper

import com.nimble.sample.database.entity.SurveyEntity
import com.nimble.sample.model.response.SurveyAttributes

fun SurveyAttributes.asEntity() = SurveyEntity(
  title = title.orEmpty(),
  description = description.orEmpty(),
  coverImageUrl = coverImageUrl,
  createdAt = createdAt.orEmpty()
)
