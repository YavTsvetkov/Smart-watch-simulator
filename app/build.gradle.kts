plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {/**/
    namespace = "com.src.smartwatchsimulator"
    compileSdk = 34


    packaging {
        resources.excludes.add("META-INF/*")
    }

    defaultConfig {
        applicationId = "com.src.smartwatchsimulator"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

repositories {
    mavenCentral()
    google()
}

dependencies {

    implementation("io.ktor:ktor-server-netty:3.0.0-beta-1")
//    implementation("io.ktor:ktor-html-builder:2.3.6")
    implementation("io.ktor:ktor-server-html-builder:3.0.0-beta-1")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.0-beta-1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0-beta-1")
    implementation("io.ktor:ktor-server-status-pages:3.0.0-beta-1")
    implementation("io.ktor:ktor-serialization-jackson:3.0.0-beta-1")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}