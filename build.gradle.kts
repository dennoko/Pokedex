// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
        // ksp
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.23-1.0.19")
    }
}
