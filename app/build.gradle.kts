plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}
configurations.all {
    resolutionStrategy {
        force("org.antlr:antlr4-runtime:4.7.1")
        force("org.antlr:antlr4-tool:4.7.1")
    }
}
android {
    compileSdkVersion(30)

    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        applicationId = "io.github.losthikking.themoviedb"
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildToolsVersion = "30.0.2"
}


dependencies {
    implementation(project(":api"))
    kapt("androidx.room:room-compiler:2.2.5")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.room:room-runtime:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.1")
    implementation("androidx.recyclerview:recyclerview:1.2.0-alpha06")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.paging:paging-runtime-ktx:3.0.0-alpha08")

    implementation("com.google.android.material:material:1.2.1")

    //dagger
    implementation("com.google.dagger:dagger:2.29.1")
    kapt("com.google.dagger:dagger-compiler:2.29.1")

    //hilt
    implementation("com.google.dagger:hilt-android:2.29.1-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.29.1-alpha")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02")
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha02")

    implementation("io.coil-kt:coil:1.0.0")

    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}


