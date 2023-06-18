package com.sina.feature_products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sina.feature_products.ui.main.ProductsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val stringExtra = intent.getIntExtra("categoryId", 0)
        val resultBundle = Bundle()
        stringExtra.let { resultBundle.putInt("categoryId", it) }

        val itemFragment = ProductsFragment()
        itemFragment.arguments = resultBundle

        if (savedInstanceState == null) {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, itemFragment).commitNow()
        }
    }
}