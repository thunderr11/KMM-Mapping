package org.mapsteadtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.unit.dp
import org.mapsteadtask.viewmodels.DataViewModel

@Composable
fun MainScreen(viewModel: DataViewModel) {
    val buildingInfo by viewModel.buildingInfos.collectAsState()
    val analyticsData by viewModel.analyticsData.collectAsState()
    val categories by viewModel.categories.collectAsState()

    // Loading states
    val isBuildingInfoLoading by viewModel.isBuildingInfoLoading.collectAsState()
    val isAnalyticsDataLoading by viewModel.isAnalyticsDataLoading.collectAsState()

    val countries = buildingInfo.map { it.country }.distinct()
    val states = buildingInfo.map { it.state }.distinct()
    val manufacturers = analyticsData.map { it.manufacturer ?: "" }.distinct()

    var selectedCountry by remember { mutableStateOf<String?>(null) }
    var selectedState by remember { mutableStateOf<String?>(null) }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedManufacturer by remember { mutableStateOf<String?>(null) }

    val filteredResults by viewModel.filteredAnalyticsData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mapstead KMM") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (isBuildingInfoLoading || isAnalyticsDataLoading) {
                    // Show loading indicator while data is being fetched
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    // Display UI only when data is loaded
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                            Text("Country", style = MaterialTheme.typography.h6)
                            DropdownMenu(
                                label = "Choose country",
                                items = countries,
                                selectedItem = selectedCountry
                            ) { country ->
                                selectedCountry = country
                                selectedState = null
                                selectedCategoryId = null
                            }
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                            Text("State", style = MaterialTheme.typography.h6)
                            DropdownMenu(
                                label = "Choose state",
                                items = states,
                                selectedItem = selectedState
                            ) { state ->
                                selectedState = state
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                            Text("Category", style = MaterialTheme.typography.h6)
                            DropdownMenu(
                                label = "Choose Category",
                                items = categories,
                                selectedItem = selectedCategoryId.toString()
                            ) { category ->
                                selectedCategoryId = category.toInt()
                            }
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                            Text("Manufacturer", style = MaterialTheme.typography.h6)
                            DropdownMenu(
                                label = "Choose Manufacturer",
                                items = manufacturers,
                                selectedItem = selectedManufacturer
                            ) { manufacturer ->
                                selectedManufacturer = manufacturer
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.filterAnalyticsData(
                                selectedCountry,
                                selectedState,
                                selectedCategoryId,
                                selectedManufacturer
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Results")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        items(filteredResults) { result ->
                            //holds list data UI in a different composable function
                            ListItemScreen(result)
                        }
                    }
                }
            }
        }
    )
}