package com.sina.feature_item

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sina.feature_details.R
import com.sina.feature_item.ui.main.ItemFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ItemActivity : AppCompatActivity() {
    private val TAG = "ItemActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val stringExtra = intent.getIntExtra("productId", 0)
        val resultBundle = Bundle()
        stringExtra?.toInt()?.let { resultBundle.putInt("productId", it) }

        val itemFragment = ItemFragment()
        itemFragment.arguments = resultBundle

        if (savedInstanceState == null) {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, itemFragment).commitNow()
        }
    }
}