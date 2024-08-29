package org.mapsteadtask.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.mapsteadtask.data.models.AnalyticsData
import org.mapsteadtask.data.models.BuildingInfo

class NetworkService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    suspend fun getBuildingInfos(): List<BuildingInfo> {
        val response: HttpResponse = client.get("https://rnd-interview.mapsted.com/GetBuildingData/")
        return response.body()
    }

    suspend fun getAnalyticsData(): List<AnalyticsData> {
        val response: HttpResponse = client.get("https://rnd-interview.mapsted.com/GetAnalyticData/")
        return response.body()
    }
}