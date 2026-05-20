package com.echosystem.localshare.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    object Keys {
        val DEVICE_NAME = stringPreferencesKey("device_name")
        val SERVER_PORT = intPreferencesKey("server_port")
        val AUTO_START_BOOT = booleanPreferencesKey("auto_start_boot")
        val REQUIRE_PIN = booleanPreferencesKey("require_pin")
        val PIN_VALUE = stringPreferencesKey("pin_value")
    }

    fun deviceName(): Flow<String> = dataStore.data.map { it[Keys.DEVICE_NAME] ?: "EchoSystem" }
    fun serverPort(): Flow<Int> = dataStore.data.map { it[Keys.SERVER_PORT] ?: 8080 }
    fun autoStartOnBoot(): Flow<Boolean> = dataStore.data.map { it[Keys.AUTO_START_BOOT] ?: false }
    fun requirePin(): Flow<Boolean> = dataStore.data.map { it[Keys.REQUIRE_PIN] ?: false }

    suspend fun updateDeviceName(name: String) { dataStore.edit { it[Keys.DEVICE_NAME] = name } }
    suspend fun updateAutoStart(enabled: Boolean) { dataStore.edit { it[Keys.AUTO_START_BOOT] = enabled } }
    suspend fun updateRequirePin(enabled: Boolean) { dataStore.edit { it[Keys.REQUIRE_PIN] = enabled } }
    suspend fun updatePin(pin: String) { dataStore.edit { it[Keys.PIN_VALUE] = pin } }
}
