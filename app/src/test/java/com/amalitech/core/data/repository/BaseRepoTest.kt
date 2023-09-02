package com.amalitech.core.data.repository

import com.amalitech.core.util.ApiResult
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class BaseRepoTest {

    @MockK
    private lateinit var response: Response<String>
    private lateinit var baseRepo: BaseRepo

    @Before
    fun setUp() {
        response = Response.success("success")
        baseRepo = BaseRepo()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `ensures the success response is returned`() = runTest {
        val result = baseRepo.safeApiCall(
            apiToBeCalled = {
                return@safeApiCall response
            },
            extractError = {
                baseRepo.extractError(it)
            }
        )

        assertEquals(ApiResult("success"), result)
    }

//    @Test
//    fun `ensures handleError returns the right error`() {
//        val json = """
//            {
//              "status": "false",
//              "errors": {
//                "name": [
//                  "name unauthorized",
//                  "yes"
//                ],
//                "email": [
//                  "email unauthorized",
//                  "yes"
//                ]
//              }
//            }
//        """.trimIndent()
//        response = Response.error(
//            404,
//            json.toResponseBody(("application/json").toMediaTypeOrNull())
//        )
//        @MockK
//        val jsonObject = JSONObject(json)
//        val errorBody = response.errorBody()
//        every { jsonObject.has(any()) } returns true
//
//        val error = baseRepo.handleErrors(
//            errorBody = errorBody,
//            extractError = {
//                baseRepo.extractError(jsonObject)
//            }
//        )
//
//        assertEquals(UiText.DynamicString("name unauthorized"), error)
//    }
}
