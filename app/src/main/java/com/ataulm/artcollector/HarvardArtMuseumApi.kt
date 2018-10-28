package com.ataulm.artcollector

import com.squareup.moshi.Json
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET

internal interface HarvardArtMuseumApi {

    @GET("object?$PAINTINGS_&$WITH_IMAGES_&$INC_FIELDS")
    fun paintings(): Deferred<ApiResponse>

    companion object {

        const val ENDPOINT = "https://api.harvardartmuseums.org"
        private const val PAINTINGS_ = "classification=26"
        private const val WITH_IMAGES_ = "hasimage=1"
        private const val INC_FIELDS = "fields=id,title,description,primaryimageurl"
    }
}

internal class AddApiKeyQueryParameterInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
                .addQueryParameter("apikey", apiKey)
                .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

internal data class ApiResponse(
        @Json(name = "info") val info: ApiInfo,
        @Json(name = "records") val records: List<ApiRecord>
)

internal data class ApiInfo(
        @Json(name = "totalrecordsperquery") val totalRecordsPerQuery: Int,
        @Json(name = "totalrecords") val totalRecords: Int,
        @Json(name = "pages") val pages: Int,
        @Json(name = "page") val page: Int,
        @Json(name = "next") val next: String
)

internal data class ApiRecord(
        @Json(name = "id") val id: Int,
        @Json(name = "title") val title: String,
        @Json(name = "description") val description: String?,
        @Json(name = "primaryimageurl") val primaryImageUrl: String?
)