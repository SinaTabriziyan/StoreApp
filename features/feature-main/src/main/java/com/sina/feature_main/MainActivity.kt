package com.sina.feature_main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sina.common.navigator.Navigator

class MainActivity : AppCompatActivity() {
    companion object {
        fun launchActivity(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
object GoToMainActivity : Navigator {
    override fun navigate(activities: Activity) = MainActivity.launchActivity(activities)
}