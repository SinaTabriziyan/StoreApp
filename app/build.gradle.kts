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
    implementation(project(mapOf("path" to ":features:feature-category")))
    implementation(project(mapOf("path" to ":features:feature-main")))
    implementation(project(mapOf("path" to ":data:data-home")))
    implementation(project(mapOf("path" to ":domain:domain-main")))
    implementation(project(mapOf("path" to ":features:feature-home")))
    implementation(project(mapOf("path" to ":features:feature-category")))
    implementation(project(mapOf("path" to ":data:data-item")))
    implementation(project(mapOf("path" to ":data:data-search")))


    implementation(project(":core:common"))
    implementation(project(":core:local"))
    implementation(project(":core:network"))

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

    implementation(DI.hilt)
    kapt(DI.hiltCompiler)
    kapt(DI.hiltAndroidCompiler)

    implementation(RestApi.retrofit)
    implementation(RestApi.gsonConverter)
    implementation(RestApi.okHttpLoggingInterceptor)
    implementation(RestApi.scalarConverter)



    implementation(ORM.room)
    kapt(ORM.roomCompiler)
    implementation(ORM.roomKtx)

    implementation(Preferences.datastorePreferences)
    implementation(ImageLoaders.glide)
    implementation(ImageLoaders.coil)
    implementation(ProgressBar.circularBar)
    implementation(Animations.lottie)
}