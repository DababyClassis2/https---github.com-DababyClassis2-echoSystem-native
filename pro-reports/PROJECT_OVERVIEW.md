# Project Overview: EchoSystem LocalShare

## Summary
EchoSystem LocalShare is a peer-to-peer (P2P) local file transfer application designed to operate within LAN environments without requiring an external internet connection.

## Purpose
To provide a secure, fast, and easy-to-use method for sharing files between devices on the same local network using multiple discovery and transfer protocols.

## Core Features
- **Multi-protocol Discovery**: BLE, NSD, WiFi Direct, and UDP broadcast.
- **File Transfer**: Robust file transfer mechanism using Ktor HTTP/WebSocket.
- **Security**: Secure pairing and encrypted storage of credentials.
- **UI/UX**: Modern Android interface built with Jetpack Compose.

## Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose, Material3
- **DI**: Dagger Hilt
- **Networking**: Ktor Server (Netty) & Client
- **Architecture**: MVVM with ViewModel and Repository pattern
- **Build System**: Gradle 8.x

## Modules
- **app**: Single module containing all application logic, UI, and networking.
