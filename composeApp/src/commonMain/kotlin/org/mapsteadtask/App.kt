package org.mapsteadtask

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mapsteadtask.data.repos.DataRepository
import org.mapsteadtask.network.NetworkService
import org.mapsteadtask.ui.MainScreen
import org.mapsteadtask.viewmodels.DataViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = DataViewModel(
            repository = DataRepository(NetworkService())
        )
        MainScreen(viewModel = viewModel)

    }
}

