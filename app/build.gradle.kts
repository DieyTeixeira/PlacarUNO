@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "br.com.dieyteixeira.placaruno"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.dieyteixeira.placaruno"
        minSdk = 24
        targetSdk = 34
        versionCode = 10
        versionName = "1.9.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.animation)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.runtime.livedata)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.material)
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat.v170)
    implementation(libs.material)
    implementation(libs.animation)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Dependências do Firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore.ktx)

    // Dependência para link de gif
    implementation(libs.coil.compose)

    // Dependência para video
    implementation(libs.exoplayer)
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.ui)
    implementation(libs.exoplayer.hls)
    implementation(libs.exoplayer.dash)
    implementation(libs.exoplayer.smoothstreaming)
    implementation(libs.extension.mediasession)
}