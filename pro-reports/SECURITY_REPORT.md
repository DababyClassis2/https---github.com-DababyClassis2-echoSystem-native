# Security Report

## Authentication & Storage
- Uses `androidx.security.crypto` with `MasterKey` (AES256_GCM) and `EncryptedSharedPreferences` for secure credential storage.

## Network Security
- `network_security_config.xml` allows cleartext traffic for local LAN communication, essential for P2P file transfers.

## Permissions
- Extensive permissions required for:
  - Network (`INTERNET`, `WIFI`)
  - Location/Scanning (`NEARBY_WIFI_DEVICES`, `BLUETOOTH_SCAN`)
  - Storage (scoped for modern Android)
  - Foreground Services

## Recommendations
- Regularly audit permissions to ensure compliance with privacy best practices.
- Ensure that the generated TLS certificates for P2P connections are handled securely and rotated.
