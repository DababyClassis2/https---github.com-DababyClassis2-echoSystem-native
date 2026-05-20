package com.echosystem.localshare.repository

import com.echosystem.localshare.model.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRegistry @Inject constructor() {
    private val devices = ConcurrentHashMap<String, Device>()
    private val _devicesFlow = MutableStateFlow<List<Device>>(emptyList())
    val devicesFlow: StateFlow<List<Device>> = _devicesFlow.asStateFlow()

    fun register(device: Device) {
        devices[device.id] = device
        updateFlow()
    }

    fun unregister(deviceId: String) {
        devices.remove(deviceId)
        updateFlow()
    }

    fun getAll(): List<Device> {
        return devices.values.toList()
    }

    fun getById(id: String): Device? {
        return devices[id]
    }

    fun clear() {
        devices.clear()
        updateFlow()
    }

    private fun updateFlow() {
        _devicesFlow.value = devices.values.toList()
    }
}
