package com.echosystem.localshare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.echosystem.localshare.ui.viewmodel.FilesViewModel

@Composable
fun FilesScreen(viewModel: FilesViewModel = hiltViewModel()) {
    val files by viewModel.receivedFiles.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(files) { file ->
            Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                Text(text = file.name, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
