package com.echosystem.localshare.server

import com.echosystem.localshare.model.ServerEvent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerEventBus @Inject constructor() {
    private val _flow = MutableSharedFlow<ServerEvent>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val flow: SharedFlow<ServerEvent> = _flow.asSharedFlow()

    suspend fun emit(event: ServerEvent) {
        _flow.emit(event)
    }

    fun tryEmit(event: ServerEvent): Boolean {
        return _flow.tryEmit(event)
    }
}
