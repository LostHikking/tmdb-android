val kotlinVersion by extra("1.3.72")
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        kotlin("kotlin-gradle-plugin")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version "4.3.0"
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("**/*.kt")
        ktlint("0.37.2")
    }
}

