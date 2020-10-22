plugins {
    `java-library`
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.4.10"
}

sourceSets {
    main {
        java.srcDir("src/main/kotlin")
    }
}

dependencies {
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
}
