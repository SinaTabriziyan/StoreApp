package com.sina.feature_customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sina.feature_customer.ui.main.CustomerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CustomerFragment())
                .commitNow()
        }
    }
}