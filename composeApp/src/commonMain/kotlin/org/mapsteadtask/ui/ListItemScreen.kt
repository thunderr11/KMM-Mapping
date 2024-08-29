package org.mapsteadtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mapsteadtask.data.models.AnalyticsData

@Composable
fun ListItemScreen(result: AnalyticsData){
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Manufacturer: ${result.manufacturer}",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Model: ${result.model}",
                    style = MaterialTheme.typography.body1,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                result.usageStatistics?.sessionInfos?.forEach { session ->
                    Text(
                        text = "Building ID: ${session.buildingId}",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    session.purchases.forEach { purchase ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = 2.dp,
                            backgroundColor = androidx.compose.ui.graphics.Color.LightGray
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(text = "Item ID: ${purchase.itemId}")
                                Text(text = "Cost: ${purchase.cost}")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}