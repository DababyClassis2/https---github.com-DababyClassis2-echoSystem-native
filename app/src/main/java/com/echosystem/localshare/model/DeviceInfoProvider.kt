package com.echosystem.localshare.model

import java.net.Inet4Address
import java.net.NetworkInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceInfoProvider @Inject constructor() {

    fun getIpAddress(): String {
        val interfaces = NetworkInterface.getNetworkInterfaces().toList()
        for (networkInterface in interfaces) {
            for (inetAddress in networkInterface.inetAddresses.toList()) {
                if (inetAddress is Inet4Address && !inetAddress.isLoopbackAddress) {
                    return inetAddress.hostAddress ?: ""
                }
            }
        }
        return ""
    }
}
