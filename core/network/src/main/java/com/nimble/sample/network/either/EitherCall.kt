package com.nimble.sample.network.either

import arrow.core.Either
import java.lang.reflect.Type
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class EitherCall<R>(
  private val call: Call<R>,
  private val responseType: Type
) : Call<Either<ErrorResponse, R>> {

  override fun enqueue(callback: Callback<Either<ErrorResponse, R>>) {
    call.enqueue(
      object : Callback<R> {
        override fun onResponse(call: Call<R>, response: Response<R>) {
          val either = if (!response.isSuccessful) {
            val error = try {
              response.errorBody().use { e ->
                ErrorResponse.from(e?.string(), statusCode = response.code())
              }
            } catch (e: Exception) {
              ErrorResponse(listOf(ErrorDetail(detail = e.localizedMessage, code = response.message())))
            }
            Either.Left(error)
          } else {
            val body = response.body()
            if (body == null) {
              Either.Left(ErrorResponse(listOf(ErrorDetail(detail = "Not found", code = response.message()))))
            } else {
              Either.Right(body)
            }
          }
          callback.onResponse(this@EitherCall, Response.success(either))
        }

        override fun onFailure(call: Call<R>, throwable: Throwable) {
          val error = ErrorResponse(listOf(ErrorDetail(detail = throwable.localizedMessage)))
          callback.onResponse(this@EitherCall, Response.success(Either.Left(error)))
        }
      }
    )
  }

  override fun isExecuted(): Boolean {
    return call.isExecuted
  }

  override fun clone(): Call<Either<ErrorResponse, R>> {
    return EitherCall(call.clone(), responseType)
  }

  override fun isCanceled(): Boolean {
    return call.isCanceled
  }

  override fun cancel() {
    call.cancel()
  }

  override fun execute(): Response<Either<ErrorResponse, R>> {
    val response = call.execute()
    val either = if (!response.isSuccessful) {
      val error = try {
        response.errorBody().use { e ->
          ErrorResponse.from(e?.string(), response.code())
        }
      } catch (e: Exception) {
        ErrorResponse(listOf(ErrorDetail(detail = e.localizedMessage, source = null)))
      }
      Either.Left(error)
    } else {
      val body = response.body()
      if (body == null) {
        Either.Left(ErrorResponse(listOf(ErrorDetail(detail = "Not found", source = null, code = "400"))))
      } else {
        Either.Right(body)
      }
    }
    return Response.success(either)
  }

  override fun request(): Request {
    return call.request()
  }

  override fun timeout(): Timeout {
    return call.timeout()
  }
}
