package com.echosystem.localshare.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.echosystem.localshare.repository.SettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject lateinit var settingsRepository: SettingsRepository
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            scope.launch {
                if (settingsRepository.autoStartOnBoot().first()) {
                    // LocalShareForegroundService.start(context)
                }
            }
        }
    }
}
