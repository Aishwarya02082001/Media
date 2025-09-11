plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mediahmi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mediahmi"
        minSdk = 28
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("src\\main\\java", "src\\main\\java\\service",
                    "src\\main\\java",
                    "src\\main\\java\\activity", "src\\main\\java", "com\\example\\mediahmi\\activity",
                    "src\\main\\java",
                    "com\\example\\mediahmi\\service", "src\\main\\java", "src\\main\\java\\activity",
                    "src\\main\\java",
                    "src\\main\\java\\service", "src\\main\\java", "service"
                )
            }
        }
    }
}


configurations.all {
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
}

dependencies {

    implementation(libs.appcompat)
    implementation(project(":shared"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.media)
}