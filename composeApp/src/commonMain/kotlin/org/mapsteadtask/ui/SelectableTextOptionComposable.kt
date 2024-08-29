package org.mapsteadtask.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectableTextOptionComposable(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.White
    val borderColor = if (isSelected) MaterialTheme.colors.primary else Color.Gray
    val textColor = if (isSelected) MaterialTheme.colors.primary else Color.Gray

    Box(
        modifier = modifier
            .padding(4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(4.dp))
            .border(BorderStroke(2.dp, borderColor), shape = RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear selection",
                    tint = textColor,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onClear() }
                )
            }
        }
    }
}