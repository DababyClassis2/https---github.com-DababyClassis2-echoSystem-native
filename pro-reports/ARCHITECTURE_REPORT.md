# Architecture Report

## Overview
The application follows the MVVM architectural pattern, leveraging Hilt for Dependency Injection.

## High-Level Data Flow
```text
[Discovery Modules] -> [DeviceRegistry] -> [UI (DevicesScreen)]
                                  |
[UI (FilesScreen)] -> [FileTransferManager] -> [Ktor Routes] -> [FileRepository]
```

## Layers
- **UI Layer**: Compose-based screens, ViewModels for state management.
- **Data Layer**: Repositories (`FileRepository`, `DeviceRegistry`, `SettingsRepository`) manage data sources.
- **Network Layer**: Ktor-based embedded HTTP server for handling incoming file requests and P2P communication.
- **Discovery Layer**: Modular implementations (BLE, NSD, etc.) to detect peers.
- **Security Layer**: Encrypted storage using `androidx.security`.

## Dependency Injection (Hilt)
Managed by `@SingletonComponent`. Modules are defined for Network (`provideHttpClient`), Data (`provideDataStore`), and Server Routes.

## Security Layer
Uses `EncryptedSharedPreferences` for secure storage of pairing PINs and tokens.
