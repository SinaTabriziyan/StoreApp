package com.sina.feature_customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sina.feature_customer.ui.main.UserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserFragment())
                .commitNow()
        }
    }
}