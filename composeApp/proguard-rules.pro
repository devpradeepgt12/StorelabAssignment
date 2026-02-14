# ProGuard rules for Ktor, which is used by Coil for networking.
# These rules prevent R8 from removing classes that are used via reflection.
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
