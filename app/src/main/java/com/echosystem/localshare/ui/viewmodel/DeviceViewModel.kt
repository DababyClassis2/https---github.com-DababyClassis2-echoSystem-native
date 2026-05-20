package com.echosystem.localshare.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.echosystem.localshare.model.Device
import com.echosystem.localshare.discovery.NsdDiscovery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val nsdDiscovery: NsdDiscovery
) : ViewModel() {
    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices = _devices.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning = _isScanning.asStateFlow()

    fun triggerScan() {
        // Implement scanning logic using discovery classes
    }

    fun addManualDevice(ip: String) {
        // Implement manual connection
    }
}
