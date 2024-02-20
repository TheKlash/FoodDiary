plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "de.alekseipopov.fooddiary"
    compileSdk = 34

    defaultConfig {
        applicationId = "de.alekseipopov.fooddiary"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Koin
    runtimeOnly("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-core-coroutines:3.5.3")
    implementation("io.insert-koin:koin-android:3.5.3")
    implementation("io.insert-koin:koin-android-test:3.5.3")
    implementation("io.insert-koin:koin-androidx-workmanager:3.5.3")
    runtimeOnly("io.insert-koin:koin-compose:1.1.2")
    runtimeOnly("io.insert-koin:koin-androidx-compose:3.5.3")
    implementation("io.insert-koin:koin-androidx-compose-navigation:3.5.3")

    //Navigation component
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")
}