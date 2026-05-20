# Ktor + Netty
-keep class io.ktor.** { *; }
-keep class io.netty.** { *; }
-dontwarn io.netty.**
-dontwarn io.ktor.**

# Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keep,includedescriptorclasses class com.echosystem.localshare.**$$serializer { *; }
-keepclassmembers class com.echosystem.localshare.** {
    *** Companion;
}
-keepclasseswithmembers class com.echosystem.localshare.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# SLF4J (pulled in by Ktor/Netty)
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }

# Keep model classes for serialization
-keep class com.echosystem.localshare.model.** { *; }

# BlockHound (Transitive dependency cleanup)
-dontwarn reactor.blockhound.**
-dontwarn reactor.blockhound.integration.BlockHoundIntegration

# Keep repository and bus classes
-keep class com.echosystem.localshare.repository.** { *; }
-keep class com.echosystem.localshare.server.ServerEventBus { *; }
