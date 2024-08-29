package org.mapsteadtask.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mapsteadtask.data.models.AnalyticsData
import org.mapsteadtask.data.models.BuildingInfo
import org.mapsteadtask.data.repos.DataRepository

class DataViewModel(private val repository: DataRepository) {

    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Loading states for the APIs
    private val _isBuildingInfoLoading = MutableStateFlow(false)
    val isBuildingInfoLoading: StateFlow<Boolean> get() = _isBuildingInfoLoading

    private val _isAnalyticsDataLoading = MutableStateFlow(false)
    val isAnalyticsDataLoading: StateFlow<Boolean> get() = _isAnalyticsDataLoading

    private val _buildingInfos = MutableStateFlow<List<BuildingInfo>>(emptyList())
    val buildingInfos: StateFlow<List<BuildingInfo>> get() = _buildingInfos

    private val _analyticsData = MutableStateFlow<List<AnalyticsData>>(emptyList())
    val analyticsData: StateFlow<List<AnalyticsData>> get() = _analyticsData

    // Holds the filtered analytics data based on the selected dropdown values
    private val _filteredAnalyticsData = MutableStateFlow<List<AnalyticsData>>(emptyList())
    val filteredAnalyticsData: StateFlow<List<AnalyticsData>> get() = _filteredAnalyticsData

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> get() = _categories

    init {
        loadBuildingInfos()
        loadAnalyticsData()
    }

    /**
     * Load building information from the repository.
     */
    fun loadBuildingInfos() {
        viewModelScope.launch {
            _isBuildingInfoLoading.value = true
            try {
                val data = repository.fetchBuildingInfos()
                _buildingInfos.value = data
            } catch (e: Exception) {
                print("DataViewModel --- >Error loading building infos")
            } finally {
                _isBuildingInfoLoading.value = false
            }
        }
    }

    /**
     * Load analytics data from the repository and prepare categories.
     */
    fun loadAnalyticsData() {
        viewModelScope.launch {
            _isAnalyticsDataLoading.value = true
            try {
                val data = repository.fetchAnalyticsData()
                _analyticsData.value = data

                // setting data for categories
                _categories.value = data.flatMap { it.usageStatistics?.sessionInfos!! }
                    .flatMap { session -> session.purchases.map { it.itemCategoryId.toString() } }
                    .distinct().sortedBy { it }
            } catch (e: Exception) {
                print("DataViewModel -- >>>>> Error loading analytics data")
            } finally {
                _isAnalyticsDataLoading.value = false
            }
        }
    }

    /**
     * Filter analytics data based on the selected dropdown values.
     */
    fun filterAnalyticsData(
        selectedCountry: String?,
        selectedState: String?,
        selectedCategoryId: Int?,
        selectedManufacturer: String?
    ) {
        _filteredAnalyticsData.value = _analyticsData.value.filter { data ->
            (selectedManufacturer == null || data.manufacturer == selectedManufacturer) &&
                    data.usageStatistics?.sessionInfos?.any { session ->
                        // Find the building info associated with the session
                        val buildingInfo = _buildingInfos.value.find { it.building_id == session.buildingId }
                        (selectedCountry == null || buildingInfo?.country == selectedCountry) &&
                                (selectedState == null || buildingInfo?.state == selectedState) &&
                                // Filter purchases based on the selected category ID
                                (selectedCategoryId == null || session.purchases.any { purchase -> purchase.itemCategoryId == selectedCategoryId })
                    } == true
        }
    }
}