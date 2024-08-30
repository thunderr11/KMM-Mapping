package org.mapsteadtask.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mapsteadtask.viewmodels.DataViewModel

@Composable
fun MainScreen(viewModel: DataViewModel) {
    val buildingInfo by viewModel.buildingInfos.collectAsState()
    val analyticsData by viewModel.analyticsData.collectAsState()
    val categories by viewModel.categories.collectAsState()

    // Convert strings to integers, sort them, and convert back to strings
    val sortedCategories = categories
        .mapNotNull { it.toIntOrNull() } // Convert to Int, ignore non-numeric values
        .sorted() // Sort the integers
        .map { it.toString() } // Convert back to String
    // Loading states
    val isBuildingInfoLoading by viewModel.isBuildingInfoLoading.collectAsState()
    val isAnalyticsDataLoading by viewModel.isAnalyticsDataLoading.collectAsState()

    val countries = buildingInfo.map { it.country }.distinct()
    val states = buildingInfo.map { it.state }.distinct()
    val manufacturers = analyticsData.map { it.manufacturer ?: "" }.distinct()

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var selectedDropdownValue by remember { mutableStateOf<String?>(null) }

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
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Choose an option",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        val options = listOf("Manufacturer", "Country", "Category ID", "State")

                        // First Row ---- creating two selectable options in 1st row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            options.subList(0, 2).forEach { option ->
                                SelectableTextOptionComposable(
                                    text = option,
                                    isSelected = selectedOption == option,
                                    onClick = {
                                        selectedOption = option
                                        selectedDropdownValue = null
                                    },
                                    modifier = Modifier.weight(1f),
                                    onClear = {
                                        // Clearing the selection
                                        selectedOption = null
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Second Row ------creating other two selectable options in 2nd row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            options.subList(2, 4).forEach { option ->
                                SelectableTextOptionComposable(
                                    text = option,
                                    isSelected = selectedOption == option,
                                    onClick = {
                                        selectedOption = option
                                        selectedDropdownValue = null
                                    },
                                    modifier = Modifier.weight(1f),
                                    onClear = {
                                        // Clearing the selection
                                        selectedOption = null
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // drop down menu dynamic heading
                    selectedOption?.let {
                        Text(
                            text = "Choose a $selectedOption",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // preparing dropdown menu according to $selectedOption
                    when (selectedOption) {
                        "Manufacturer" -> DropdownMenu(
                            label = "Choose Manufacturer",
                            items = manufacturers,
                            selectedItem = selectedDropdownValue
                        ) { value ->
                            selectedDropdownValue = value
                        }
                        "Country" -> DropdownMenu(
                            label = "Choose Country",
                            items = countries,
                            selectedItem = selectedDropdownValue
                        ) { value ->
                            selectedDropdownValue = value
                        }
                        "Category ID" -> DropdownMenu(
                            label = "Choose Category",
                            items = sortedCategories,
                            selectedItem = selectedDropdownValue
                        ) { value ->
                            selectedDropdownValue = value
                        }
                        "State" -> DropdownMenu(
                            label = "Choose State",
                            items = states,
                            selectedItem = selectedDropdownValue
                        ) { value ->
                            selectedDropdownValue = value
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    selectedDropdownValue?.let {
                        Text(
                            text = "You have selected a $selectedOption named $selectedDropdownValue.",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    )
}