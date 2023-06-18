plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sina.data_home"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    implementation(project(mapOf("path" to ":core:model")))
    implementation(project(mapOf("path" to ":core:network")))
    implementation(project(mapOf("path" to ":core:common")))

    implementation(Deps.core)
    implementation(Deps.appcompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)
    implementation(project(mapOf("path" to ":domain:domain-main")))
    implementation(project(mapOf("path" to ":core:common")))
    implementation(project(mapOf("path" to ":data:category")))


    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.testJUnit)
    androidTestImplementation(TestImpl.espresso)

    implementation(DI.hilt)
    kapt(DI.hiltCompiler)
    kapt(DI.hiltAndroidCompiler)

}