plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.sina.store"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.sina.store"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(Deps.core)
    implementation(Deps.appcompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)


    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.testJUnit)
    androidTestImplementation(TestImpl.espresso)

    implementation(LifeCycle.lifeCycleViewModel)
    implementation(LifeCycle.lifeCycleViewLivedata)

    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)

    implementation(RestApi.retrofit)
    implementation(RestApi.gsonConverter)
    implementation(RestApi.okHttpLoggingInterceptor)
    implementation(RestApi.scalarConverter)

    implementation(DI.hilt)
    kapt(DI.hiltCompiler)

    implementation(ORM.room)
    kapt(ORM.roomCompiler)
    implementation(ORM.roomKtx)

    implementation(Preferences.datastorePreferences)
    implementation(ImageLoaders.glide)
    implementation(ImageLoaders.coil)
    implementation(ProgressBar.circularBar)
    implementation(Animations.lottie)
}