package com.echosystem.localshare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.echosystem.localshare.ui.viewmodel.DeviceViewModel

@Composable
fun DevicesScreen(viewModel: DeviceViewModel = hiltViewModel()) {
    val devices by viewModel.devices.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (isScanning) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn {
                items(devices) { device ->
                    Text(text = device.name, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
