package com.amalitech.core.data.repository

import com.amalitech.core.R
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

/**
 * BaseRepo - A base class for all repositories.
 */
open class BaseRepo {
    /**
     * safeApiCall - Makes a call to the api based on the input,
     * and handles errors.
     * @param apiToBeCalled The suspend function that makes the api call.
     * It always returns a retrofit Response object that can be used to determine
     * whether the call is successful or not.
     * @param extractError The function which is responsible of converting the
     * list of errors into an instance of UiText. It returns the UiText instance.
     * @return an instance of ApiResult.
     */
    suspend fun <T> safeApiCall(
        apiToBeCalled: suspend () -> Response<T>,
        extractError: (jsonObject: JSONObject) -> UiText?
    ): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    val body = response.body()
                    ApiResult(data = body)
                } else {
                    val errorResponse = handleErrors(
                        errorBody = response.errorBody(),
                        extractError = extractError
                    )
                    ApiResult(error = errorResponse)
                }

            } catch (e: IOException) {
                ApiResult(error = UiText.StringResource(R.string.error_check_your_internet_and_try_again_please))
            } catch (e: Exception) {
                ApiResult(error = e.localizedMessage?.let { UiText.DynamicString(it) })
            }
        }
    }

    private fun handleErrors(
        errorBody: ResponseBody?,
        extractError: (JSONObject) -> UiText?
    ): UiText {
//        try {
            val responseBody = errorBody?.string()
            responseBody?.let {
                val jsonObject = JSONObject(responseBody)
                return extractError(jsonObject) ?: UiText.StringResource(R.string.error_default_message)
            }
            return UiText.StringResource(R.string.error_default_message)
      /*  } catch (e: Exception) {
            e.localizedMessage?.let {
                return UiText.DynamicString(it)
            }
            return UiText.StringResource(R.string.error_default_message)
        }*/
    }

    fun extractError(jsonObject: JSONObject): UiText? {
        if (jsonObject.has("errors")) {
            val errorsObject = jsonObject.getJSONObject("errors")
            val errorFields = errorsObject.keys()
            for (fields in errorFields) {
                val errorArray = errorsObject.getJSONArray(fields)
                val errors = mutableListOf<String>()
                for (i in 0 until errorArray.length()) {
                    errors.add(errorArray.getString(i))
                }
                if (errors.isNotEmpty())
                    return UiText.DynamicString(errors.first())
            }
        }
        if (jsonObject.has("error")) {
            return UiText.DynamicString(jsonObject.getString("error"))
        }
        if (jsonObject.has("message")) {
            return UiText.DynamicString(jsonObject.getString("message"))
        }
        return null
    }
}
