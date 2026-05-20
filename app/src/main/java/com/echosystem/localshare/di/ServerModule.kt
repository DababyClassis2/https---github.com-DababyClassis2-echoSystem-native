package com.echosystem.localshare.di

import com.echosystem.localshare.repository.DeviceRegistry
import com.echosystem.localshare.repository.FileRepository
import com.echosystem.localshare.server.ServerEventBus
import com.echosystem.localshare.server.routes.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {

    @Provides
    @Singleton
    fun provideFileRoutes(fileRepository: FileRepository, serverEventBus: ServerEventBus) = FileRoutes(fileRepository, serverEventBus)

    @Provides
    @Singleton
    fun provideDeviceRoutes(deviceRegistry: DeviceRegistry, deviceInfoProvider: com.echosystem.localshare.model.DeviceInfoProvider) = DeviceRoutes(deviceRegistry, deviceInfoProvider)

    @Provides
    @Singleton
    fun providePairingRoutes(pairingManager: com.echosystem.localshare.security.PairingManager) = PairingRoutes(pairingManager)

    @Provides
    @Singleton
    fun provideWebSocketRoutes(serverEventBus: ServerEventBus) = WebSocketRoutes(serverEventBus)
    
    @Provides
    @Singleton
    fun provideFileRepository(context: android.content.Context) = FileRepository(context)
    
    @Provides
    @Singleton
    fun provideDeviceRegistry() = DeviceRegistry()
    
    @Provides
    @Singleton
    fun provideServerEventBus() = ServerEventBus()
}
