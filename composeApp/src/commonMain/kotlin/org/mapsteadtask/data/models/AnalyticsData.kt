package org.mapsteadtask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnalyticsData(
    @SerialName("manufacturer")
    var manufacturer: String? = null,
    @SerialName("market_name")
    var marketName: String? = null,
    @SerialName("codename")
    var codename: String?= null,
    @SerialName("model")
    var model: String?= null,
    @SerialName("usage_statistics" )
    var usageStatistics : UsageStatistics? = null

)

@Serializable
data class Purchases (

    @SerialName("item_id")
    var itemId : Int?    = null,
    @SerialName("item_category_id" )
    var itemCategoryId : Int? = null,
    @SerialName("cost")
    var cost : Double? = null

)

@Serializable
data class SessionInfos (

    @SerialName("building_id" ) var buildingId : Int? = null,
    @SerialName("purchases") var purchases  : ArrayList<Purchases> = arrayListOf()

)

@Serializable
data class UsageStatistics (
    @SerialName("session_infos") var sessionInfos : ArrayList<SessionInfos> = arrayListOf()
)


