package com.nimble.sample.network.either

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

data class ErrorDetail(
  @JsonProperty("detail")
  val detail: String? = null,

  @JsonProperty("source")
  val source: String? = null,

  @JsonProperty("code")
  val code: String = "400"
)

data class ErrorResponse(
  val errors: List<ErrorDetail>
) {

  internal companion object {

    private val JACKSON = ObjectMapper().apply {
      registerModule(KotlinModule())
      configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
      configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    internal fun from(json: String?, statusCode: Int): ErrorResponse {
      return if (json == null) {
        ErrorResponse(listOf(ErrorDetail(code = statusCode.toString(), source = "Something went wrong!")))
      } else {
        try {
          JACKSON
            .readValue(json, ErrorResponse::class.java)
        } catch (e: Exception) {
          ErrorResponse(listOf(ErrorDetail(code = statusCode.toString(), detail = json)))
        }
      }
    }
  }
}
