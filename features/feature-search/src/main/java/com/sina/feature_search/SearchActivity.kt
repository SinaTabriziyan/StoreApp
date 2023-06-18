package com.sina.feature_search

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sina.feature_search.databinding.ActivitySearchBinding
import com.sina.ui_components.BaseActivity
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
    }

    override fun setupViews() {}
    override fun animationStatus(state: BaseViewModel.UiState) {}
}