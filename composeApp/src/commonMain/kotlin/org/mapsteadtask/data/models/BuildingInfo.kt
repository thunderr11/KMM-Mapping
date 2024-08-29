package org.mapsteadtask.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BuildingInfo(val building_id: Int,
                        val building_name : String,
                        val city: String,
                        val state: String,
                        val country: String)
