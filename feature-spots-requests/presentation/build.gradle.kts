plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.google.dagger.hilt.android)
}

android {
    namespace = "com.yervand.feature.spots.request.presentation"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(projects.featureSpotsRequests.domain)
    implementation(projects.featureSpotsRequests.shared)
    implementation(projects.featureSpotsInfo.shared)
    implementation(projects.core.utils)
    implementation(projects.core.entities)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.dagger.hilt.android)
    kapt(libs.hilt.android.compiler)
}

