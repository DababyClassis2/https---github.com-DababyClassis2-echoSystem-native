package com.echosystem.localshare.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.echosystem.localshare.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val deviceName = settingsRepository.deviceName()
}
