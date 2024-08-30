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

    // HttpClient initialization with JSON configuration
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    // Function to fetch building information
    suspend fun getBuildingInfos(): List<BuildingInfo> {
        val response: HttpResponse = client.get("${BASE_URL}${Endpoints.GET_BUILDING_DATA}")
        return response.body()
    }

    // Function to fetch analytics data
    suspend fun getAnalyticsData(): List<AnalyticsData> {
        val response: HttpResponse = client.get("${BASE_URL}${Endpoints.GET_ANALYTIC_DATA}")
        return response.body()
    }

    companion object {
        // Base URL for the API
        private const val BASE_URL = "https://rnd-interview.mapsted.com/"

        // API Endpoints
        private object Endpoints {
            const val GET_BUILDING_DATA = "GetBuildingData/"
            const val GET_ANALYTIC_DATA = "GetAnalyticData/"
        }
    }
}