package org.mapsteadtask.data.repos

import org.mapsteadtask.data.models.AnalyticsData
import org.mapsteadtask.data.models.BuildingInfo
import org.mapsteadtask.network.NetworkService

class DataRepository(private val networkService: NetworkService) {

    suspend fun fetchBuildingInfos(): List<BuildingInfo> {
        return networkService.getBuildingInfos()
    }

    suspend fun fetchAnalyticsData(): List<AnalyticsData> {
        return networkService.getAnalyticsData()
    }
}