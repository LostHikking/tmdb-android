plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(30)

    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        applicationId = "io.github.losthikking.themoviedb"
        minSdkVersion(29)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildToolsVersion = "30.0.1"
}


dependencies {
    kapt("androidx.room:room-compiler:2.2.5")
    //kotlin("kotlin-stdlib-jdk8:1.3.72")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.room:room-runtime:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    implementation("androidx.recyclerview:recyclerview:1.2.0-alpha05")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.paging:paging-runtime-ktx:3.0.0-alpha06")

    implementation("com.google.android.material:material:1.2.1")
    implementation("com.google.code.gson:gson:2.8.6")

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    implementation("org.koin:koin-android:2.1.6")
    implementation("org.koin:koin-android-viewmodel:2.1.6")

    implementation("io.coil-kt:coil:1.0.0-rc3")
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
