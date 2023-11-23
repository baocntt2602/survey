package com.nimble.sample.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nimble.sample.model.response.SurveyAttributes

@Entity(tableName = "survey")
data class SurveyEntity(
  @PrimaryKey
  val title: String,
  val description: String,
  @ColumnInfo(name = "cover_image_url")
  val coverImageUrl: String,
  @ColumnInfo(name = "created_at")
  val createdAt: String,
)

fun SurveyEntity.asExternalModel() = SurveyAttributes(
  title = title,
  description = description,
  coverImageUrl = coverImageUrl,
  createdAt = createdAt
)
